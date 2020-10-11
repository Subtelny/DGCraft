package pl.subtelny.islands.skyblockisland.creator;

import org.bukkit.Location;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.schematic.SchematicLoader;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.creator.IslandCreator;
import pl.subtelny.islands.island.repository.IslandRepository;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.skyblockisland.SkyblockIslandQueryService;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandCache;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class SkyblockIslandCreator implements IslandCreator<SkyblockIsland, CreateSkyblockIslandRequest> {

    private final TransactionProvider transactionProvider;

    private final SkyblockIslandCache skyblockIslandCache;

    private final IslandRepository islandRepository;

    private final SkyblockIslandQueryService skyblockIslandQueryService;

    private final SkyblockIslandExtendCuboidCalculator cuboidCalculator;

    private final SkyblockIslandSettings settings;

    @Autowired
    public SkyblockIslandCreator(TransactionProvider transactionProvider,
                                 SkyblockIslandCache skyblockIslandCache,
                                 IslandRepository islandRepository, SkyblockIslandQueryService skyblockIslandQueryService,
                                 SkyblockIslandExtendCuboidCalculator cuboidCalculator,
                                 SkyblockIslandSettings settings) {
        this.transactionProvider = transactionProvider;
        this.skyblockIslandCache = skyblockIslandCache;
        this.islandRepository = islandRepository;
        this.skyblockIslandQueryService = skyblockIslandQueryService;
        this.cuboidCalculator = cuboidCalculator;
        this.settings = settings;
    }

    @Override
    public CompletableFuture<SkyblockIsland> create(CreateSkyblockIslandRequest request) {
        IslandCoordinates islandCoordinates = request.getCoordinates().orElse(getNextIslandCoordinates());
        SkyblockIslandSchematicOption schematicOption = request.getOption().orElse(settings.getDefaultSchematicOption());
        return createSkyblockIsland(request.getIslander(), islandCoordinates, schematicOption);
    }

    private IslandCoordinates getNextIslandCoordinates() {
        return skyblockIslandCache.nextFreeIslandCoordinates()
                .orElseThrow(() -> ValidationException.of("creator.skyblockIsland.no_free_island_coordinates"));
    }

    private CompletableFuture<SkyblockIsland> createSkyblockIsland(Islander islander, IslandCoordinates coordinates, SkyblockIslandSchematicOption schematic) {
        validateIslander(islander);
        validateIslandCoordinates(coordinates);
        Cuboid cuboid = cuboidCalculator.calculateCuboid(coordinates);
        return new IslandSchematicPaster(cuboid.getCenter(), schematic)
                .pasteSchematic()
                .thenCompose(unused -> new IslandCreatorJob(cuboid, coordinates, islander).createIsland());
    }

    private void validateIslander(Islander islander) {
        Optional<SkyblockIsland> skyblockIsland = islander.getSkyblockIsland();
        Validation.isTrue(skyblockIsland.isEmpty(), "creator.skyblockIsland.already_has_island");
    }

    private void validateIslandCoordinates(IslandCoordinates islandCoordinates) {
        boolean islandFound = skyblockIslandQueryService.findSkyblockIsland(islandCoordinates).isEmpty();
        Validation.isTrue(islandFound, "creator.skyblockIsland.coordinates_taken");
    }

    private class IslandCreatorJob {

        private final Cuboid cuboid;

        private final IslandCoordinates islandCoordinates;

        private final Islander islander;

        private IslandCreatorJob(Cuboid cuboid,
                                 IslandCoordinates islandCoordinates,
                                 Islander islander) {
            this.cuboid = cuboid;
            this.islandCoordinates = islandCoordinates;
            this.islander = islander;
        }

        public CompletableFuture<SkyblockIsland> createIsland() {
            return transactionProvider.transactionResultAsync(this::createSkyblockIsland).toCompletableFuture();
        }

        private SkyblockIsland createSkyblockIsland() {
            Location spawn = new SkyblockIslandSpawnCalculator(cuboid).calculate();
            SkyblockIsland skyblockIsland = new SkyblockIsland(spawn, cuboid, islandCoordinates);
            skyblockIsland.join(islander);
            IslandId islandId = islandRepository.updateIsland(skyblockIsland);
            return skyblockIslandQueryService.findSkyblockIsland(islandId)
                    .orElseThrow(() -> new IllegalStateException("Not found just created a skyblock island " + islandId));
        }

    }

    private static class IslandSchematicPaster {

        private final Location location;

        private final SkyblockIslandSchematicOption schematicOption;

        private IslandSchematicPaster(Location location, SkyblockIslandSchematicOption schematicOption) {
            this.location = location;
            this.schematicOption = schematicOption;
        }

        public CompletableFuture<Void> pasteSchematic() {
            return JobsProvider.runAsync(() -> {
                try {
                    CountDownLatch latch = new CountDownLatch(1);
                    File schematicFile = new File(schematicOption.getSchematicFilePath());
                    validateSchematicFile(schematicFile);
                    SchematicLoader.pasteSchematic(location, schematicFile, aBoolean -> latch.countDown());
                    latch.await(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    throw ValidationException.of("creator.skyblockIsland.error_while_pasting_island");
                }
            });
        }

        private void validateSchematicFile(File schematicFile) {
            Validation.isTrue(schematicFile.exists(),
                    "creator.skyblockIsland.schema_file_not_found",
                    schematicOption.getSchematicFilePath());
        }

    }

}



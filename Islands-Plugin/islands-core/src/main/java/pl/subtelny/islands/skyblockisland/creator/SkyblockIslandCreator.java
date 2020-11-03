package pl.subtelny.islands.skyblockisland.creator;

import org.bukkit.Location;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.worldedit.SchematicPasteSession;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.islandold.creator.IslandCreator;
import pl.subtelny.islands.islandold.repository.IslandRepository;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.skyblockisland.SkyblockIslandQueryService;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandCache;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.job.Job;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
        validateIslander(request.getIslander());
        IslandCoordinates islandCoordinates = request.getCoordinates().orElseGet(this::getNextIslandCoordinates);
        validateIslandCoordinates(islandCoordinates);

        Cuboid cuboid = cuboidCalculator.calculateCuboid(islandCoordinates);
        SkyblockIslandSchematicOption schematicOption = request.getOption().orElseGet(settings::getDefaultSchematicOption);
        IslandCreateStateListener stateListener = request.getStateListener().orElse(null);
        return pasteSchematic(cuboid.getCenter(), schematicOption, stateListener)
                .thenCompose(v -> createSkyblockIsland(request.getIslander(), islandCoordinates, cuboid));
    }

    private IslandCoordinates getNextIslandCoordinates() {
        return skyblockIslandCache.nextFreeIslandCoordinates()
                .orElseThrow(() -> ValidationException.of("creator.skyblockIsland.no_free_island_coordinates"));
    }

    private CompletableFuture<Void> pasteSchematic(Location location, SkyblockIslandSchematicOption schematic, @Nullable IslandCreateStateListener stateListener) {
        File schematicFile = new File(schematic.getSchematicFilePath());
        Validation.isTrue(schematicFile.exists(), "creator.skyblockIsland.schematic_not_found");
        SchematicPasteSession pasteSession = new SchematicPasteSession(schematicFile, location);
        if (stateListener != null) {
            pasteSession.setStateListener(stateListener::state);
        }
        return pasteSession.performAsync();
    }

    private CompletableFuture<SkyblockIsland> createSkyblockIsland(Islander islander, IslandCoordinates coordinates, Cuboid cuboid) {
        SkyblockIslandCreatorJob islandCreatorJob = new SkyblockIslandCreatorJob(cuboid, coordinates, islander);
        return transactionProvider.transactionResultAsync(islandCreatorJob::execute).toCompletableFuture();
    }

    private void validateIslander(Islander islander) {
        Optional<Island> island = islander.findIsland(SkyblockIsland.TYPE);
        Validation.isTrue(island.isEmpty(), "creator.skyblockIsland.already_has_island");
    }

    private void validateIslandCoordinates(IslandCoordinates islandCoordinates) {
        boolean islandFound = skyblockIslandQueryService.findSkyblockIsland(islandCoordinates).isEmpty();
        Validation.isTrue(islandFound, "creator.skyblockIsland.coordinates_taken");
    }

    private class SkyblockIslandCreatorJob implements Job<SkyblockIsland> {

        private final Cuboid cuboid;

        private final IslandCoordinates islandCoordinates;

        private final Islander islander;

        private SkyblockIslandCreatorJob(Cuboid cuboid,
                                         IslandCoordinates islandCoordinates,
                                         Islander islander) {
            this.cuboid = cuboid;
            this.islandCoordinates = islandCoordinates;
            this.islander = islander;
        }

        @Override
        public SkyblockIsland execute() {
            return createSkyblockIsland();
        }

        private SkyblockIsland createSkyblockIsland() {
            Location spawn = calculateSpawn();
            SkyblockIsland skyblockIsland = new SkyblockIsland(spawn, cuboid, islandCoordinates);
            skyblockIsland.join(islander);
            skyblockIsland.changeOwner(islander);
            IslandId islandId = islandRepository.updateIsland(skyblockIsland);
            return skyblockIslandQueryService.findSkyblockIsland(islandId)
                    .orElseThrow(() -> new IllegalStateException("Not found just created a skyblock island " + islandId));
        }

        private Location calculateSpawn() {
            try {
                return new SkyblockIslandSpawnCalculator(cuboid).calculate();
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new IllegalStateException("Could not calculate spawn for island: " + e.getMessage());
            }
        }

    }

}



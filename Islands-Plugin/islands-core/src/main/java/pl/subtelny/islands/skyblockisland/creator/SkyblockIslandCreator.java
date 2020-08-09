package pl.subtelny.islands.skyblockisland.creator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.schematic.SchematicLoader;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.cuboid.CuboidUtil;
import pl.subtelny.utilities.exception.ValidationException;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class SkyblockIslandCreator {

    private final TransactionProvider transactionProvider;

    private final SkyblockIslandRepository repository;

    private final SchematicLoader schematicLoader;

    private final SkyblockIslandExtendCuboidCalculator cuboidCalculator;

    private final SkyblockIslandSettings settings;

    @Autowired
    public SkyblockIslandCreator(TransactionProvider transactionProvider, SkyblockIslandRepository repository, SchematicLoader schematicLoader, SkyblockIslandExtendCuboidCalculator cuboidCalculator, SkyblockIslandSettings settings) {
        this.transactionProvider = transactionProvider;
        this.repository = repository;
        this.schematicLoader = schematicLoader;
        this.cuboidCalculator = cuboidCalculator;
        this.settings = settings;
    }

    public CompletableFuture<SkyblockIsland> createIsland(Islander islander, @Nullable IslandCoordinates coordinates, @Nullable SkyblockIslandSchematicOption schematic) {
        IslandCoordinates islandCoordinates = Optional.ofNullable(coordinates).orElse(getNextIslandCoordinates());
        SkyblockIslandSchematicOption schematicOption = Optional.ofNullable(schematic)
                .orElse(settings.getDefaultSchematicOption());
        return createSkyblockIsland(islander, islandCoordinates, schematicOption);
    }

    public CompletableFuture<SkyblockIsland> createIsland(Islander islander, SkyblockIslandSchematicOption schematic) {
        return createIsland(islander, null, schematic);
    }

    private CompletableFuture<SkyblockIsland> createSkyblockIsland(Islander islander, IslandCoordinates coordinates, SkyblockIslandSchematicOption schematic) {
        validateIslander(islander);
        validateIslandCoordinates(coordinates);
        Cuboid cuboid = cuboidCalculator.calculateCuboid(coordinates);
        return transactionProvider.transactionResultAsync(() -> {
            pasteSchematic(schematic, cuboid.getCenter());
            Location spawn = new SkyblockIslandSpawnCalculator(cuboid).calculate();
            return repository.createIsland(coordinates, spawn, cuboid, islander);
        }).toCompletableFuture();
    }

    private void pasteSchematic(SkyblockIslandSchematicOption schematic, Location center) {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            File schematicFile = new File(schematic.getSchematicFilePath());
            Validation.isTrue(schematicFile.exists(), "creator.skyblockIsland.schema_file_not_found", schematic.getSchematicFilePath());
            schematicLoader.pasteSchematic(center, schematicFile, aBoolean -> latch.countDown());
            latch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw ValidationException.of("creator.skyblockIsland.error_while_pasting_island");
        }
    }

    private IslandCoordinates getNextIslandCoordinates() {
        return repository.nextFreeIslandCoordinates()
                .orElseThrow(() -> ValidationException.of("creator.skyblockIsland.no_free_island_coordinates"));
    }

    private void validateIslander(Islander islander) {
        Optional<SkyblockIsland> skyblockIsland = islander.getSkyblockIsland();
        Validation.isTrue(skyblockIsland.isEmpty(), "creator.skyblockIsland.already_has_island");
    }

    private void validateIslandCoordinates(IslandCoordinates islandCoordinates) {
        Validation.isTrue(repository.findSkyblockIsland(islandCoordinates).isEmpty(), "creator.skyblockIsland.coordinates_taken");
    }

}



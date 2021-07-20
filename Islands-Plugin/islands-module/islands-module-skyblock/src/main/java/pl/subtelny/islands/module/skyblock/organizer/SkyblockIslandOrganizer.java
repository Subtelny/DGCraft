package pl.subtelny.islands.module.skyblock.organizer;

import org.bukkit.Location;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.worldedit.OperationStatus;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.configuration.Configuration;
import pl.subtelny.islands.module.skyblock.IslandExtendCalculator;
import pl.subtelny.islands.module.skyblock.configuration.SkyblockIslandModuleConfiguration;
import pl.subtelny.islands.module.skyblock.organizer.generator.SkyblockIslandGenerator;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.io.File;

public class SkyblockIslandOrganizer {

    private final SkyblockIslandGenerator islandGenerator;

    private final SkyblockIslandCleaner islandCleaner;

    private final SkyblockIslandCoordinates coordinates;

    private final IslandExtendCalculator extendCalculator;

    private final Configuration<SkyblockIslandModuleConfiguration> configuration;

    private final ConnectionProvider connectionProvider;

    public SkyblockIslandOrganizer(Configuration<SkyblockIslandModuleConfiguration> configuration,
                                   IslandType islandType,
                                   ConnectionProvider connectionProvider) {
        this.configuration = configuration;
        this.connectionProvider = connectionProvider;
        this.islandGenerator = new SkyblockIslandGenerator();
        this.islandCleaner = new SkyblockIslandCleaner();
        this.coordinates = new SkyblockIslandCoordinates(islandType);
        this.extendCalculator = new IslandExtendCalculator(configuration);
    }

    public void initialize() {
        coordinates.initialize(connectionProvider);
    }

    public GenerateResult generateIsland(Callback<OperationStatus> statusCallback) {
        IslandCoordinates freeCoords = coordinates.getFreeCoords();
        Cuboid cuboid = extendCalculator.calculateFullyCuboid(freeCoords);

        SkyblockIslandModuleConfiguration islandConfig = configuration.get();
        int schematicHeight = islandConfig.getSchematicHeight();
        File schematicFile = islandConfig.getSchematicFile();
        Location center = cuboid.getCenter();
        center.setY(schematicHeight);

        islandGenerator.generateIsland(center, schematicFile, statusCallback);
        return new GenerateResult(center, freeCoords);
    }

    public void cleanIsland(IslandCoordinates islandCoordinates) {
        Cuboid cuboid = extendCalculator.calculateFullyCuboid(islandCoordinates);
        islandCleaner.cleanBlocksAt(cuboid);
        coordinates.addFreeCoords(islandCoordinates);
    }

}

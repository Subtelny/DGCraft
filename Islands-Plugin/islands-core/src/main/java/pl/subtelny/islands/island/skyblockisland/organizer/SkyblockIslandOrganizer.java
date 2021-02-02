package pl.subtelny.islands.island.skyblockisland.organizer;

import org.bukkit.Location;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.worldedit.OperationStatus;
import pl.subtelny.islands.island.IslandCoordinates;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.Configuration;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.organizer.generator.SkyblockIslandGenerator;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.io.File;

public class SkyblockIslandOrganizer {

    private final SkyblockIslandGenerator islandGenerator;

    private final SkyblockIslandCleaner islandCleaner;

    private final SkyblockIslandCoordinates coordinates;

    private final IslandExtendCalculator extendCalculator;

    private final Configuration<SkyblockIslandConfiguration> configuration;

    public SkyblockIslandOrganizer(Configuration<SkyblockIslandConfiguration> configuration,
                                   IslandType islandType) {
        this.configuration = configuration;
        this.islandGenerator = new SkyblockIslandGenerator();
        this.islandCleaner = new SkyblockIslandCleaner();
        this.coordinates = new SkyblockIslandCoordinates(islandType);
        this.extendCalculator = new IslandExtendCalculator(configuration);
    }

    public void initialize(ConnectionProvider connectionProvider) {
        coordinates.initialize(connectionProvider);
    }

    public GenerateResult generateIsland(Callback<OperationStatus> statusCallback) {
        IslandCoordinates freeCoords = coordinates.getFreeCoords();
        Cuboid cuboid = extendCalculator.calculateFullyCuboid(freeCoords);

        SkyblockIslandConfiguration islandConfig = configuration.get();
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

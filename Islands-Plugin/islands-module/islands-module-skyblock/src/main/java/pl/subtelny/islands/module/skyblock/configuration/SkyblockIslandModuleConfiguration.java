package pl.subtelny.islands.module.skyblock.configuration;

import org.bukkit.Location;
import pl.subtelny.islands.island.configuration.IslandExtendConfiguration;
import pl.subtelny.islands.island.module.IslandModuleConfiguration;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;

import java.io.File;

public class SkyblockIslandModuleConfiguration extends IslandModuleConfiguration {

    private final IslandExtendConfiguration extendConfiguration;

    private final int defaultSize;

    private final int spaceBetweenIslands;

    private final String schematicFilePath;

    private final int schematicHeight;

    public SkyblockIslandModuleConfiguration(String worldName,
                                             boolean createEnabled,
                                             IslandExtendConfiguration extendConfiguration,
                                             int defaultSize,
                                             int spaceBetweenIslands,
                                             String schematicFilePath,
                                             int schematicHeight,
                                             File moduleDir) {
        super(worldName, createEnabled, moduleDir);
        this.extendConfiguration = extendConfiguration;
        this.defaultSize = defaultSize;
        this.spaceBetweenIslands = spaceBetweenIslands;
        this.schematicFilePath = schematicFilePath;
        this.schematicHeight = schematicHeight;
    }

    public IslandExtendConfiguration getExtendConfiguration() {
        return extendConfiguration;
    }

    public int getTotalSize() {
        return extendConfiguration.getTotalSize()
                .orElse(defaultSize);
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public int getSpaceBetweenIslands() {
        return spaceBetweenIslands;
    }

    public File getSchematicFile() {
        return new File(schematicFilePath);
    }

    public int getSchematicHeight() {
        return schematicHeight;
    }

    public IslandCoordinates toIslandCoords(Location location) {
        int maxSize = getTotalSize() + spaceBetweenIslands;
        int blockX = location.getBlockX();
        int blockZ = location.getBlockZ();


        int x = blockX / maxSize;
        int z = blockZ / maxSize;
        return new IslandCoordinates(x, z);
    }

}

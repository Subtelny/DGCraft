package pl.subtelny.islands.island.skyblockisland.configuration;

import org.bukkit.Location;
import pl.subtelny.islands.island.skyblockisland.IslandCoordinates;
import pl.subtelny.islands.island.configuration.IslandConfiguration;
import pl.subtelny.islands.island.configuration.IslandExtendConfiguration;

import java.io.File;
import java.util.Objects;

public class SkyblockIslandConfiguration extends IslandConfiguration {

    private final IslandExtendConfiguration extendConfiguration;

    private final int defaultSize;

    private final int spaceBetweenIslands;

    private final String schematicFilePath;

    private final int schematicHeight;

    private final File moduleDir;

    public SkyblockIslandConfiguration(String worldName,
                                       boolean createEnabled,
                                       IslandExtendConfiguration extendConfiguration,
                                       int defaultSize,
                                       int spaceBetweenIslands,
                                       String schematicFilePath,
                                       int schematicHeight,
                                       File moduleDir) {
        super(worldName, createEnabled);
        this.extendConfiguration = extendConfiguration;
        this.defaultSize = defaultSize;
        this.spaceBetweenIslands = spaceBetweenIslands;
        this.schematicFilePath = schematicFilePath;
        this.schematicHeight = schematicHeight;
        this.moduleDir = moduleDir;
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

    public File getModuleDir() {
        return moduleDir;
    }

    public IslandCoordinates toIslandCoords(Location location) {
        int maxSize = getTotalSize() + spaceBetweenIslands;
        int blockX = location.getBlockX();
        int blockZ = location.getBlockZ();

        int x = blockX / maxSize;
        int z = blockZ / maxSize;
        return new IslandCoordinates(x, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandConfiguration that = (SkyblockIslandConfiguration) o;
        return defaultSize == that.defaultSize && spaceBetweenIslands == that.spaceBetweenIslands && schematicHeight == that.schematicHeight && Objects.equals(extendConfiguration, that.extendConfiguration) && Objects.equals(schematicFilePath, that.schematicFilePath) && Objects.equals(moduleDir, that.moduleDir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extendConfiguration, defaultSize, spaceBetweenIslands, schematicFilePath, schematicHeight, moduleDir);
    }
}

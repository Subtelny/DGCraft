package pl.subtelny.islands.island.skyblockisland.configuration;

import org.bukkit.Location;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.IslandCoordinates;
import pl.subtelny.islands.island.configuration.IslandConfiguration;
import pl.subtelny.islands.island.configuration.IslandExtendConfiguration;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;
import java.util.Objects;

public class SkyblockIslandConfiguration extends IslandConfiguration {

    private final IslandExtendConfiguration extendConfiguration;

    private final int defaultSize;

    private final int spaceBetweenIslands;

    private final String schematicFilePath;

    public SkyblockIslandConfiguration(String worldName,
                                       IslandExtendConfiguration extendConfiguration,
                                       int defaultSize,
                                       int spaceBetweenIslands, String schematicFilePath) {
        super(worldName);
        this.extendConfiguration = extendConfiguration;
        this.defaultSize = defaultSize;
        this.spaceBetweenIslands = spaceBetweenIslands;
        this.schematicFilePath = schematicFilePath;
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

    public String getSchematicFilePath() {
        return schematicFilePath;
    }

    public File getSchematicFile() {
        return FileUtil.getFile(Islands.plugin, schematicFilePath);
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
        return defaultSize == that.defaultSize &&
                spaceBetweenIslands == that.spaceBetweenIslands &&
                Objects.equals(extendConfiguration, that.extendConfiguration) &&
                Objects.equals(schematicFilePath, that.schematicFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extendConfiguration, defaultSize, spaceBetweenIslands, schematicFilePath);
    }
}

package pl.subtelny.islands.island.skyblockisland.configuration;

import org.bukkit.Location;
import pl.subtelny.islands.island.configuration.IslandConfiguration;
import pl.subtelny.islands.island.configuration.IslandExtendConfiguration;
import pl.subtelny.islands.island.configuration.IslandSchematicConfiguration;
import pl.subtelny.islands.islander.model.IslandCoordinates;

import java.util.Objects;

public class SkyblockIslandConfiguration extends IslandConfiguration {

    private final IslandExtendConfiguration extendConfiguration;

    private final IslandSchematicConfiguration schematicConfiguration;

    private final int defaultSize;

    private final int spaceBetweenIslands;

    public SkyblockIslandConfiguration(String worldName,
                                       IslandExtendConfiguration extendConfiguration,
                                       IslandSchematicConfiguration schematicConfiguration,
                                       int defaultSize,
                                       int spaceBetweenIslands) {
        super(worldName);
        this.extendConfiguration = extendConfiguration;
        this.schematicConfiguration = schematicConfiguration;
        this.defaultSize = defaultSize;
        this.spaceBetweenIslands = spaceBetweenIslands;
    }

    public IslandSchematicConfiguration getSchematicConfiguration() {
        return schematicConfiguration;
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
                Objects.equals(extendConfiguration, that.extendConfiguration) &&
                Objects.equals(schematicConfiguration, that.schematicConfiguration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extendConfiguration, schematicConfiguration, defaultSize);
    }

}

package pl.subtelny.islands.island.skyblockisland;

import org.bukkit.Bukkit;
import org.bukkit.World;
import pl.subtelny.islands.island.configuration.Configuration;
import pl.subtelny.islands.island.configuration.IslandExtendConfiguration;
import pl.subtelny.islands.island.configuration.IslandExtendLevel;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.List;

public class IslandExtendCalculator {

    private final Configuration<SkyblockIslandConfiguration> configuration;

    public IslandExtendCalculator(Configuration<SkyblockIslandConfiguration> configuration) {
        this.configuration = configuration;
    }

    public Cuboid calculateDefaultCuboid(IslandCoordinates islandCoordinates) {
        int defaultSize = configuration.get().getDefaultSize();
        return calculateCuboid(islandCoordinates, defaultSize);
    }

    public Cuboid calculateExtendedCuboid(IslandCoordinates islandCoordinates, int extendLevel) {
        int size = getExtendLevelSizeOrDefault(extendLevel);
        return calculateCuboid(islandCoordinates, size);
    }

    private Cuboid calculateCuboid(IslandCoordinates islandCoordinates, int size) {
        SkyblockIslandConfiguration islandConfiguration = configuration.get();
        int totalSize = islandConfiguration.getTotalSize();
        int spaceBetweenIslands = islandConfiguration.getSpaceBetweenIslands();
        World world = Bukkit.getWorld(islandConfiguration.getWorldName());
        return SkyblockIslandUtil.buildCuboid(islandCoordinates, world, totalSize, size, spaceBetweenIslands);
    }

    private int getExtendLevelSizeOrDefault(int extendLevel) {
        SkyblockIslandConfiguration islandConfiguration = configuration.get();
        IslandExtendConfiguration extendConfiguration = islandConfiguration.getExtendConfiguration();
        List<IslandExtendLevel> levels = extendConfiguration.getLevels();
        if (levels.size() >= extendLevel) {
            return levels.get(extendLevel).getSize();
        }
        return islandConfiguration.getDefaultSize();
    }

}

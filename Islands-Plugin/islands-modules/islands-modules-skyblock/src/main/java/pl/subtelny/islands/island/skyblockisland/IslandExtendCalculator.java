package pl.subtelny.islands.island.skyblockisland;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.islands.island.configuration.Configuration;
import pl.subtelny.islands.island.configuration.IslandExtendConfiguration;
import pl.subtelny.islands.island.configuration.IslandExtendLevel;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.List;

public class IslandExtendCalculator {

    private final Configuration<SkyblockIslandConfiguration> configuration;

    public IslandExtendCalculator(Configuration<SkyblockIslandConfiguration> configuration) {
        this.configuration = configuration;
    }

    public Cuboid calculateFullyCuboid(IslandCoordinates islandCoordinates) {
        return calculateCuboid(islandCoordinates, configuration.get().getTotalSize());
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
        return buildCuboid(islandCoordinates, world, totalSize, size, spaceBetweenIslands);
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

    private Cuboid buildCuboid(IslandCoordinates islandCoordinates, World islandWorld, int totalIslandSize, int islandSize, int spaceBetweenIslands) {
        Cuboid maxedCuboid = buildCuboid(islandCoordinates, islandWorld, totalIslandSize, spaceBetweenIslands);
        int toShrink = totalIslandSize - islandSize;
        return maxedCuboid.inset(Cuboid.CuboidDirection.Horizontal, toShrink);
    }

    private Cuboid buildCuboid(IslandCoordinates islandCoordinates, World islandWorld, int islandSize, int spaceBetweenIslands) {
        return calculateIslandCuboid(islandWorld,
                islandCoordinates.getX(),
                islandCoordinates.getZ(),
                islandSize,
                spaceBetweenIslands);
    }

    private Cuboid calculateIslandCuboid(World world, int x, int z, int size, int space) {
        int x1 = x * size + space;
        int z1 = z * size + space;
        int x2 = x * size + size - space;
        int z2 = z * size + size - space;

        Location firstCorner = new Location(world, x1, 0, z1);
        Location secondCorner = new Location(world, x2, 256, z2);
        return new Cuboid(firstCorner, secondCorner);
    }

}

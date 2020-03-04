package pl.subtelny.islands.utils;

import pl.subtelny.islands.settings.Settings;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.utils.cuboid.Cuboid;

public final class SkyblockIslandUtil {

	public static Cuboid buildFullyExtendedCuboid(IslandCoordinates islandCoordinates) {
		int spaceBetweenIslands = Settings.SkyblockIsland.SPACE_BETWEEN_ISLANDS;
		return buildCuboid(islandCoordinates, Settings.SkyblockIsland.ISLAND_SIZE, spaceBetweenIslands);
	}

	public static Cuboid buildExtendedCuboid(IslandCoordinates islandCoordinates, int extendLevel) {
		int totalIslandSize = Settings.SkyblockIsland.ISLAND_SIZE;
		int islandSizeToExtendLevel = Settings.SkyblockIsland.getIslandSizeToExtendLevel(extendLevel);
		int spaceBetweenIslands = (totalIslandSize - islandSizeToExtendLevel)
				+ Settings.SkyblockIsland.SPACE_BETWEEN_ISLANDS;
		return buildCuboid(islandCoordinates, islandSizeToExtendLevel, spaceBetweenIslands);
	}

	private static Cuboid buildCuboid(IslandCoordinates islandCoordinates, int islandSize, int spaceBetweenIslands) {
		World islandWorld = Settings.SkyblockIsland.ISLAND_WORLD;
		return calculateIslandCuboid(islandWorld,
				islandCoordinates.getX(),
				islandCoordinates.getZ(),
				islandSize,
				spaceBetweenIslands);
	}

	public static Cuboid calculateIslandCuboid(World world, int x, int z, int size, int space) {
		int x1 = x * size + space;
		int z1 = z * size + space;
		int x2 = x * size + size - space;
		int z2 = z * size + size - space;

		Location firstCorner = new Location(world, x1, z1, 0);
		Location secondCorner = new Location(world, x2, z2, 256);
		return new Cuboid("IslandCuboid", firstCorner, secondCorner);
	}

	public static IslandCoordinates getIslandCoordinates(Location location) {
		int blockX = location.getBlockX();
		int blockZ = location.getBlockZ();

		int x = blockX / Settings.SkyblockIsland.ISLAND_SIZE;
		int z = blockZ / Settings.SkyblockIsland.ISLAND_SIZE;
		return new IslandCoordinates(x, z);
	}

}

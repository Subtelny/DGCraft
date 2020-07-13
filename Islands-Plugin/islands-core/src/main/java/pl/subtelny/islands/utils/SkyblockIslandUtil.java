package pl.subtelny.islands.utils;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.utilities.cuboid.Cuboid;

public final class SkyblockIslandUtil {

	public static Cuboid buildCuboid(IslandCoordinates islandCoordinates, World islandWorld, int totalIslandSize, int islandSize, int spaceBetweenIslands) {
		Cuboid maxedCuboid = buildCuboid(islandCoordinates, islandWorld, totalIslandSize, spaceBetweenIslands);
		int toOutset = totalIslandSize - islandSize;
		return maxedCuboid.outset(Cuboid.CuboidDirection.Horizontal, toOutset);
	}

	public static Cuboid buildCuboid(IslandCoordinates islandCoordinates, World islandWorld, int islandSize, int spaceBetweenIslands) {
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
		return new Cuboid(firstCorner, secondCorner);
	}

	public static IslandCoordinates getIslandCoordinates(Location location, int maxIslandSize) {
		int blockX = location.getBlockX();
		int blockZ = location.getBlockZ();

		int x = blockX / maxIslandSize;
		int z = blockZ / maxIslandSize;
		return new IslandCoordinates(x, z);
	}

}

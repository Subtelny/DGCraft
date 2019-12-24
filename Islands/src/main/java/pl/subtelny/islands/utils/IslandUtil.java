package pl.subtelny.islands.utils;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.utils.cuboid.Cuboid;

public final class IslandUtil {

	public static Cuboid calculateIslandCuboid(World world, int x, int z, int size, int space) {
		int x1 = x * size + space;
		int z1 = z * size + space;
		int x2 = x * size + size - space;
		int z2 = z * size + size - space;

		Location firstCorner = new Location(world, x1, z1, 0);
		Location secondCorner = new Location(world, x2, z2, 0);
		return new Cuboid("IslandCuboid", firstCorner, secondCorner);
	}

}

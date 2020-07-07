package pl.subtelny.utilities.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class CuboidUtil {

	public static String serialize(Cuboid cuboid) {
		return null;
	}

	public static Cuboid deserialize(String serializedCuboid) {
		String[] split = serializedCuboid.split(":");

		String serializedLoc1 = split[0];
		String serializedLoc2 = split[1];

		Location loc1 = deserializeLocation(serializedLoc1);
		Location loc2 = deserializeLocation(serializedLoc2);
		return new Cuboid(loc1, loc2);
	}

	private static Location deserializeLocation(String serializedLocation) {
		String[] split = serializedLocation.split(",");
		World world = Bukkit.getWorld(split[0]);
		int x = Integer.parseInt(split[1]);
		int y = Integer.parseInt(split[2]);
		int z = Integer.parseInt(split[3]);
		return new Location(world, x, y, z);
	}

}

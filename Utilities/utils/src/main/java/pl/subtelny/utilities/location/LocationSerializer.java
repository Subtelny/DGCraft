package pl.subtelny.utilities.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class LocationSerializer {

	public static String serializeMinimalistic(Location location) {
		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		float yaw = location.getYaw();
		float pitch = location.getPitch();
		return String.format("%s,%s,%s,%s,%s,%s", world, x, y, z, yaw, pitch);
	}

	public static Location deserializeMinimalistic(String minimalistic) {
		String[] fields = minimalistic.split(",");
		World world = Bukkit.getWorld(fields[0]);
		double x = Double.parseDouble(fields[1]);
		double y = Double.parseDouble(fields[2]);
		double z = Double.parseDouble(fields[3]);
		float yaw = Float.parseFloat(fields[4]);
		float pitch = Float.parseFloat(fields[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}

}

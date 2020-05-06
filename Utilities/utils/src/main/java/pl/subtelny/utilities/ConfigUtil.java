package pl.subtelny.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public final class ConfigUtil {

    public static Location loadLocation(YamlConfiguration config, String path) {
        String rawWorld = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");

        World world = Bukkit.getWorld(rawWorld);
        if (config.contains(".yaw") || config.contains(".pitch")) {
            float yaw = (float) config.getDouble(path + ".yaw");
            float pitch = (float) config.getDouble(path + ".pitch");
            return new Location(world, x, y, z, yaw, pitch);
        }
        return new Location(world, x, y, z);
    }

    public static void setPreciseLocation(YamlConfiguration config, String path, Location location) {
        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        config.set(path + ".world", worldName);
        config.set(path + ".x", x);
        config.set(path + ".y", y);
        config.set(path + ".z", z);
        config.set(path + ".yaw", yaw);
        config.set(path + ".pitch", pitch);
    }

    public static void setBlockLocation(YamlConfiguration config, String path, Location location) {
        String worldName = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        config.set(path + ".world", worldName);
        config.set(path + ".x", x);
        config.set(path + ".y", y);
        config.set(path + ".z", z);
    }

}

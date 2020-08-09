package pl.subtelny.utilities.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;

import java.io.File;

public class LocationFileParserStrategy extends AbstractFileParserStrategy<Location> {

    public LocationFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    public LocationFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public Location load(String path) {
        String rawWorld = configuration.getString(path + ".world");
        double x = configuration.getDouble(path + ".x");
        double y = configuration.getDouble(path + ".y");
        double z = configuration.getDouble(path + ".z");
        if (rawWorld == null) {
            return null;
        }
        World world = Bukkit.getWorld(rawWorld);
        if (configuration.contains(".yaw") || configuration.contains(".pitch")) {
            float yaw = (float) configuration.getDouble(path + ".yaw");
            float pitch = (float) configuration.getDouble(path + ".pitch");
            return new Location(world, x, y, z, yaw, pitch);
        }
        return new Location(world, x, y, z);
    }

    @Override
    public LocationFileParserStrategy set(String path, Location value) {
        String worldName = value.getWorld().getName();
        double x = value.getX();
        double y = value.getY();
        double z = value.getZ();
        float yaw = value.getYaw();
        float pitch = value.getPitch();

        configuration.set(path + ".world", worldName);
        configuration.set(path + ".x", x);
        configuration.set(path + ".y", y);
        configuration.set(path + ".z", z);
        configuration.set(path + ".yaw", yaw);
        configuration.set(path + ".pitch", pitch);
        return this;
    }
}

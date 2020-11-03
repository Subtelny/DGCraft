package pl.subtelny.core.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.location.LocationFileParserStrategy;

import java.io.File;

@Component
public class Locations {

    private static final String LOCATIONS_FILE_NAME = "locations.yml";

    private Cache<String, Location> cache = Caffeine.newBuilder().build();

    private File locationsFile;

    public void initLocations(Plugin plugin) {
        locationsFile = FileUtil.copyFile(plugin, LOCATIONS_FILE_NAME);
    }

    public void set(String path, Location value) {
        new LocationFileParserStrategy(locationsFile).set(path, value).save();
        cache.put(path, value);
    }

    public Location get(String path) {
        return cache.get(path, s -> new LocationFileParserStrategy(locationsFile).load(s));
    }

}

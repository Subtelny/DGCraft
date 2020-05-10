package pl.subtelny.core.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.FileUtil;
import pl.subtelny.utilities.config.FileParserStrategy;
import pl.subtelny.utilities.config.LocationFileParserStrategy;
import pl.subtelny.utilities.config.ObjectFileParserStrategy;

import java.io.File;

@Component
public class Settings {

    private static final String CONFIG_FILE_NAME = "config.yml";

    private static final String LOCATIONS_FILE_NAME = "locations.yml";

    private Cache<String, Object> cache = Caffeine.newBuilder().build();

    private File configFile;

    private File locationsFile;

    public void initSettings(Plugin plugin) {
        configFile = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
        locationsFile = FileUtil.copyFile(plugin, LOCATIONS_FILE_NAME);
    }

    public <T> void set(String path, Class<T> clazz, T value) {
        findStrategyForType(clazz).set(path, value).save();
        cache.put(path, value);
    }

    public <T> T get(String path, Class<T> clazz) {
        return (T) cache.get(path, s -> findStrategyForType(clazz).load(s));
    }

    private FileParserStrategy findStrategyForType(Class clazz) {
        if (Location.class.equals(clazz)) {
            return new LocationFileParserStrategy(locationsFile);
        }
        return new ObjectFileParserStrategy(configFile);
    }

}

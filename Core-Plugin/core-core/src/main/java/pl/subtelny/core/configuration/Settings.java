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

    private Cache<String, Object> cache = Caffeine.newBuilder().build();

    private File file;

    public void initSettings(Plugin plugin) {
        file = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
    }

    public <T> void set(String path, Class<T> clazz, T value) {
        findStrategyForType(clazz).save(path, value);
        cache.put(path, value);
    }

    public <T> T get(String path, Class<T> clazz) {
        return (T) cache.get(path, s -> findStrategyForType(clazz).load(s));
    }

    private FileParserStrategy findStrategyForType(Class clazz) {
        if (Location.class.equals(clazz)) {
            return new LocationFileParserStrategy(file);
        }
        return new ObjectFileParserStrategy(file);
    }

}

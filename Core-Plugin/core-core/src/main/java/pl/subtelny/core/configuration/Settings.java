package pl.subtelny.core.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;

@Component
public class Settings {

    private static final String CONFIG_FILE_NAME = "config.yml";

    private Cache<String, Object> cache = Caffeine.newBuilder().build();

    private File configFile;

    public void initSettings(Plugin plugin) {
        configFile = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
    }

    public <T> void set(String path, T value) {
        new ObjectFileParserStrategy(configFile).set(path, value).save();
        cache.put(path, value);
    }

    public <T> T get(String path) {
        return (T) cache.get(path, s -> new ObjectFileParserStrategy(configFile).load(s));
    }

}

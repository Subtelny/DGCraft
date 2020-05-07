package pl.subtelny.utilities.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class AbstractFileParserStrategy<T> implements FileParserStrategy<T> {

    protected final YamlConfiguration configuration;

    protected AbstractFileParserStrategy(File file) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }
}

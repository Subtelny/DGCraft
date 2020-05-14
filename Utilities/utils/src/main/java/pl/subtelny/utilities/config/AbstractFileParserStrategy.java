package pl.subtelny.utilities.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class AbstractFileParserStrategy<T> implements FileParserStrategy<T>, Saveable {

    protected final YamlConfiguration configuration;

    protected final File file;

    public AbstractFileParserStrategy(YamlConfiguration configuration, File file) {
        this.configuration = configuration;
        this.file = file;
    }

    protected AbstractFileParserStrategy(File file) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.file = file;
    }

    @Override
    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

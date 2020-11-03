package pl.subtelny.utilities.file;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;

import java.io.File;

public class ObjectFileParserStrategy<T> extends AbstractFileParserStrategy<T> {

    public ObjectFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    public ObjectFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public T load(String path) {
        return (T) configuration.get(path);
    }

    @Override
    public Saveable set(String path, T value) {
        configuration.set(path, value);
        return this;
    }
}

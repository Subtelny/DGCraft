package pl.subtelny.utilities.config;

import java.io.File;

public class ObjectFileParserStrategy<T> extends AbstractFileParserStrategy<T> {

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

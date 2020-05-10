package pl.subtelny.utilities.config;

public interface FileParserStrategy<T> {

    T load(String path);

    Saveable set(String path, T value);

}

package pl.subtelny.utilities.config;

public interface FileParserStrategy<T> {

    T load(String path);

    void save(String path, T value);

}

package pl.subtelny.utilities.file;

public interface FileParserStrategy<T> {

    T load(String path);

    Saveable set(String path, T value);

}

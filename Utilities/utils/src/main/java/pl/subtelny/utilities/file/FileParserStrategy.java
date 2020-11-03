package pl.subtelny.utilities.file;

import pl.subtelny.utilities.Saveable;

public interface FileParserStrategy<T> {

    T load(String path);

    Saveable set(String path, T value);

}

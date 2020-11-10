package pl.subtelny.utilities.file;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class PathAbstractFileParserStrategy<T> extends AbstractFileParserStrategy<T> {

    public PathAbstractFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    protected PathAbstractFileParserStrategy(File file) {
        super(file);
    }

    public abstract String getPath();

}

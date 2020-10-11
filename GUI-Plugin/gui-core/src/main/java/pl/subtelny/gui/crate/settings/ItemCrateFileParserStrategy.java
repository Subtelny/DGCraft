package pl.subtelny.gui.crate.settings;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.gui.crate.model.ItemCrate;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;

public class ItemCrateFileParserStrategy extends AbstractFileParserStrategy<ItemCrate> {

    public ItemCrateFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    protected ItemCrateFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public ItemCrate load(String path) {
        return null;
    }

    @Override
    public Saveable set(String path, ItemCrate value) {
        throw new UnsupportedOperationException("Saving ItemCrate is not implemented yet");
    }

}

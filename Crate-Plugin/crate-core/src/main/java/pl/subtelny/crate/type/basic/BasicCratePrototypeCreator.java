package pl.subtelny.crate.type.basic;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.creator.AbstractCratePrototypeCreator;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.item.ItemCrateFileParserStrategy;

import java.io.File;

public class BasicCratePrototypeCreator extends AbstractCratePrototypeCreator {

    private final ItemCrateFileParserStrategy itemCrateFileParserStrategy;

    public BasicCratePrototypeCreator(ItemCrateFileParserStrategy itemCrateFileParserStrategy) {
        this.itemCrateFileParserStrategy = itemCrateFileParserStrategy;
    }

    @Override
    public CratePrototype create(File file, YamlConfiguration config, String path) {
        return new BasicCratePrototype(
                getCrateId(file),
                getSize(config, path),
                getTitle(config, path),
                getPermission(config, path),
                getContent(config, path),
                isShared(config, path)
        );
    }

    @Override
    public CrateType getCrateType() {
        return BasicCratePrototype.TYPE;
    }

    @Override
    protected ItemCrate getItemCrate(String path) {
        return itemCrateFileParserStrategy.load(path);
    }
}

package pl.subtelny.crate.type.personal;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.creator.AbstractCratePrototypeCreator;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.item.ItemCrate;
import pl.subtelny.crate.item.ItemCrateFileParserStrategy;
import pl.subtelny.crate.prototype.CratePrototype;

import java.io.File;

public class PersonalCratePrototypeCreator extends AbstractCratePrototypeCreator {

    private final ItemCrateFileParserStrategy itemCrateFileParserStrategy;

    public PersonalCratePrototypeCreator(ItemCrateFileParserStrategy itemCrateFileParserStrategy) {
        this.itemCrateFileParserStrategy = itemCrateFileParserStrategy;
    }

    @Override
    public CratePrototype create(File file, YamlConfiguration config, String path) {
        return new PersonalCratePrototype(
                getCrateId(file),
                getSize(config, path),
                getTitle(config, path),
                getPermission(config, path),
                getContent(config, path(path, "content"))
        );
    }

    @Override
    public CrateType getCrateType() {
        return PersonalCratePrototype.TYPE;
    }

    @Override
    protected ItemCrate getItemCrate(String path) {
        return itemCrateFileParserStrategy.load(path);
    }
}

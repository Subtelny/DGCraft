package pl.subtelny.crate.type.personal;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.creator.AbstractCratePrototypeCreator;
import pl.subtelny.utilities.file.FileParserStrategy;

import java.io.File;

public class PersonalCratePrototypeCreator extends AbstractCratePrototypeCreator<PersonalCratePrototype> {

    private final FileParserStrategy<ItemCrate> itemCrateFileParserStrategy;

    public PersonalCratePrototypeCreator(FileParserStrategy<ItemCrate> itemCrateFileParserStrategy) {
        this.itemCrateFileParserStrategy = itemCrateFileParserStrategy;
    }

    @Override
    public PersonalCratePrototype create(File file, YamlConfiguration config, String path) {
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

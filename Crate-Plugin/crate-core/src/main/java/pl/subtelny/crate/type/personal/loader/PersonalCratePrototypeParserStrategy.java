package pl.subtelny.crate.type.personal.loader;

import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.parser.CratePrototypeParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.type.personal.PersonalCratePrototype;

import java.io.File;
import java.util.Map;

public class PersonalCratePrototypeParserStrategy extends CratePrototypeParserStrategy {

    public PersonalCratePrototypeParserStrategy(File file, String crateKeyPrefix, ItemCrateParserStrategy itemCrateParserStrategy) {
        super(file, crateKeyPrefix, itemCrateParserStrategy);
    }

    @Override
    public CratePrototype load(String path) {
        BasicInformation basicInformation = loadBasicInformation(path + ".configuration");
        Map<Integer, ItemCrate> content = loadContent(path + ".content");
        return new PersonalCratePrototype(
                basicInformation.crateKey,
                basicInformation.title,
                basicInformation.permission,
                basicInformation.inventorySize,
                content
        );
    }

}

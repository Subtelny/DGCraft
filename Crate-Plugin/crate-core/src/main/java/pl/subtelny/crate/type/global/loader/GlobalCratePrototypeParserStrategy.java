package pl.subtelny.crate.type.global.loader;

import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.parser.CratePrototypeParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.type.global.GlobalCratePrototype;

import java.io.File;
import java.util.Map;

public class GlobalCratePrototypeParserStrategy extends CratePrototypeParserStrategy {

    public GlobalCratePrototypeParserStrategy(File file, String crateKeyPrefix, ItemCrateParserStrategy itemCrateParserStrategy) {
        super(file, crateKeyPrefix, itemCrateParserStrategy);
    }

    @Override
    public CratePrototype load(String path) {
        BasicInformation basicInformation = loadBasicInformation(path + ".configuration");
        Map<Integer, ItemCrate> content = loadContent(path + ".content");
        return new GlobalCratePrototype(
                basicInformation.crateKey,
                basicInformation.crateType,
                basicInformation.title,
                basicInformation.permission,
                basicInformation.inventorySize,
                content
        );
    }

}

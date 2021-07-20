package pl.subtelny.crate.creator;

import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototypeCreator;
import pl.subtelny.crate.api.prototype.paged.PagedCratePrototype;
import pl.subtelny.crate.item.ItemCrateFileParserStrategy;
import pl.subtelny.crate.type.basic.BasicCratePrototype;
import pl.subtelny.crate.type.basic.BasicCratePrototypeCreator;
import pl.subtelny.crate.type.paged.PagedCratePrototypeCreator;
import pl.subtelny.crate.type.personal.PersonalCratePrototype;
import pl.subtelny.crate.type.personal.PersonalCratePrototypeCreator;
import pl.subtelny.utilities.file.FileParserStrategy;

import java.util.Arrays;
import java.util.List;

public final class DefaultCratePrototypeCreators {

    public static CratePrototypeCreator<?> getDefaultCratePrototypeCreator(CrateType crateType, FileParserStrategy<ItemCrate> itemCrateFileParserStrategy) {
        if (BasicCratePrototype.TYPE.equals(crateType)) {
            return new BasicCratePrototypeCreator(itemCrateFileParserStrategy);
        }
        if (PersonalCratePrototype.TYPE.equals(crateType)) {
            return new PersonalCratePrototypeCreator(itemCrateFileParserStrategy);
        }
        if (PagedCratePrototype.TYPE.equals(crateType)) {
            return new PagedCratePrototypeCreator(itemCrateFileParserStrategy);
        }
        throw new IllegalStateException("Not found creator for type " + crateType.getValue());
    }

    public static List<CratePrototypeCreator> getDefaultCratePrototypeCreators(FileParserStrategy<ItemCrate> itemCrateFileParserStrategy) {
        return Arrays.asList(
                new BasicCratePrototypeCreator(itemCrateFileParserStrategy),
                new PersonalCratePrototypeCreator(itemCrateFileParserStrategy),
                new PagedCratePrototypeCreator(itemCrateFileParserStrategy)
        );
    }

}

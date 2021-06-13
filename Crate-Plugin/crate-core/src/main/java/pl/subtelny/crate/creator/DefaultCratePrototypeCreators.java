package pl.subtelny.crate.creator;

import pl.subtelny.crate.item.ItemCrateFileParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototypeCreator;
import pl.subtelny.crate.type.basic.BasicCratePrototypeCreator;
import pl.subtelny.crate.type.paged.PagedCratePrototypeCreator;
import pl.subtelny.crate.type.personal.PersonalCratePrototypeCreator;

import java.util.Arrays;
import java.util.List;

public class DefaultCratePrototypeCreators {

    public static List<CratePrototypeCreator> getDefaultCratePrototypeCreators(ItemCrateFileParserStrategy itemCrateFileParserStrategy) {
        return Arrays.asList(
                new BasicCratePrototypeCreator(itemCrateFileParserStrategy),
                new PersonalCratePrototypeCreator(itemCrateFileParserStrategy),
                new PagedCratePrototypeCreator(itemCrateFileParserStrategy)
        );
    }

}

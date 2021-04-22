package pl.subtelny.crate.creator;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.item.ItemCrateFileParserStrategy;
import pl.subtelny.crate.prototype.CratePrototypeCreator;
import pl.subtelny.crate.type.basic.BasicCratePrototypeCreator;
import pl.subtelny.crate.type.paged.PagedCratePrototypeCreator;
import pl.subtelny.crate.type.personal.PersonalCratePrototypeCreator;

import java.util.Arrays;
import java.util.List;

@Component
public class DefaultCratePrototypeCreators {

    public List<CratePrototypeCreator> getDefaultCratePrototypeCreators(ItemCrateFileParserStrategy itemCrateFileParserStrategy) {
        return Arrays.asList(
                new BasicCratePrototypeCreator(itemCrateFileParserStrategy),
                new PersonalCratePrototypeCreator(itemCrateFileParserStrategy),
                new PagedCratePrototypeCreator(itemCrateFileParserStrategy)
        );
    }

}

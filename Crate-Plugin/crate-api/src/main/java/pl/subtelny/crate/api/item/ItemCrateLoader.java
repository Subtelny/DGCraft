package pl.subtelny.crate.api.item;

import pl.subtelny.utilities.file.FileParserStrategy;

public interface ItemCrateLoader {

    FileParserStrategy<ItemCrate> getItemCrateFileParserStrategy(ItemCrateLoadRequest request);

}

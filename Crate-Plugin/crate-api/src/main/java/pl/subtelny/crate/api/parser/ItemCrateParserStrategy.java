package pl.subtelny.crate.api.parser;

import pl.subtelny.crate.api.ItemCrate;

public interface ItemCrateParserStrategy {

    ItemCrate load(String path);

}

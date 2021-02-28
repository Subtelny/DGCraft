package pl.subtelny.crate.parser;

import pl.subtelny.crate.ItemCrate;

public interface ItemCrateParserStrategy {

    ItemCrate load(String path);

}

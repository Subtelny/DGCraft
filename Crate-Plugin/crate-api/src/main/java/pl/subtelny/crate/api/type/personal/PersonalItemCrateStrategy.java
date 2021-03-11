package pl.subtelny.crate.api.type.personal;

import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.utilities.messages.Messages;

public class PersonalItemCrateStrategy implements ItemCrateParserStrategy {

    private final Messages messages;

    private final ItemCrateParserStrategy itemCrateParserStrategy;

    public PersonalItemCrateStrategy(Messages messages,
                                     ItemCrateParserStrategy itemCrateParserStrategy) {
        this.messages = messages;
        this.itemCrateParserStrategy = itemCrateParserStrategy;
    }

    @Override
    public ItemCrate load(String path) {
        ItemCrate itemCrate = itemCrateParserStrategy.load(path);
        return new PersonalItemCrate(messages, itemCrate);
    }
}

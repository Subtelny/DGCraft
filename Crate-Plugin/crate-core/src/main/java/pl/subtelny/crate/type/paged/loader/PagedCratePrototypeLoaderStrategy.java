package pl.subtelny.crate.type.paged.loader;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.paged.PagedCratePrototype;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.loader.CratePrototypeLoaderStrategy;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.crate.api.type.basic.BasicItemCrateParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.type.paged.parser.PagedCratePrototypeParserStrategy;
import pl.subtelny.crate.api.type.personal.PersonalItemCrate;

@Component
public class PagedCratePrototypeLoaderStrategy implements CratePrototypeLoaderStrategy {

    private final CrateMessages messages;

    @Autowired
    public PagedCratePrototypeLoaderStrategy(CrateMessages messages) {
        this.messages = messages;
    }

    @Override
    public CratePrototype load(CratePrototypeLoadRequest request) {
        PagedCratePrototypeParserStrategy strategy = getPagedCrateStrategy(request);
        return strategy.load("");
    }

    @Override
    public CrateType getType() {
        return PagedCratePrototype.TYPE;
    }

    private PagedCratePrototypeParserStrategy getPagedCrateStrategy(CratePrototypeLoadRequest request) {
        ItemCrateParserStrategy itemCrateStrategy = getItemCrateStrategy(request);
        return new PagedCratePrototypeParserStrategy(request.getFile(), itemCrateStrategy, request.getCrateKeyPrefix());
    }

    private ItemCrateParserStrategy getItemCrateStrategy(CratePrototypeLoadRequest request) {
        BasicItemCrateParserStrategy itemCrateParserStrategy = getBasicItemCrate(request);
        return new PagedItemCrateParserStrategy(itemCrateParserStrategy);
    }

    private BasicItemCrateParserStrategy getBasicItemCrate(CratePrototypeLoadRequest request) {
        return new BasicItemCrateParserStrategy(
                request.getFile(),
                request.getCostConditionFileParserStrategy(),
                request.getConditionFileParserStrategy(),
                request.getRewardFileParserStrategy()
        );
    }

    private class PagedItemCrateParserStrategy implements ItemCrateParserStrategy {

        private final ItemCrateParserStrategy itemCrateParserStrategy;

        private PagedItemCrateParserStrategy(ItemCrateParserStrategy itemCrateParserStrategy) {
            this.itemCrateParserStrategy = itemCrateParserStrategy;
        }

        @Override
        public ItemCrate load(String path) {
            ItemCrate itemCrate = itemCrateParserStrategy.load(path);
            return new PersonalItemCrate(messages, itemCrate);
        }
    }

}

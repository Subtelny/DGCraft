package pl.subtelny.crate.type.global.loader;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.loader.CratePrototypeLoaderStrategy;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.crate.api.parser.BasicItemCrateParserStrategy;
import pl.subtelny.crate.api.parser.CratePrototypeParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.global.GlobalCrateType;
import pl.subtelny.crate.type.global.GlobalItemCrate;

import java.io.File;

@Component
public class GlobalCratePrototypeLoaderStrategy implements CratePrototypeLoaderStrategy {

    private final CrateMessages messages;

    @Autowired
    public GlobalCratePrototypeLoaderStrategy(CrateMessages messages) {
        this.messages = messages;
    }

    @Override
    public CratePrototype load(CratePrototypeLoadRequest request) {
        File file = request.getFile();
        ItemCrateParserStrategy itemCrateStrategy = getItemCrateParserStrategy(request);
        return new CratePrototypeParserStrategy(file, request.getPlugin(), request.getCrateKeyPrefix(), itemCrateStrategy).load("");
    }

    @Override
    public CrateType getType() {
        return GlobalCrateType.TYPE;
    }

    private ItemCrateParserStrategy getItemCrateParserStrategy(CratePrototypeLoadRequest request) {
        BasicItemCrateParserStrategy basicItemCrate = getBasicItemCrate(request);
        return new GlobalItemCrateStrategy(basicItemCrate);
    }

    private BasicItemCrateParserStrategy getBasicItemCrate(CratePrototypeLoadRequest request) {
        return new BasicItemCrateParserStrategy(
                request.getFile(),
                request.getCostConditionFileParserStrategy(),
                request.getConditionFileParserStrategy(),
                request.getRewardFileParserStrategy()
        );
    }

    private class GlobalItemCrateStrategy implements ItemCrateParserStrategy {

        private final ItemCrateParserStrategy itemCrateParserStrategy;

        private GlobalItemCrateStrategy(ItemCrateParserStrategy itemCrateParserStrategy) {
            this.itemCrateParserStrategy = itemCrateParserStrategy;
        }

        @Override
        public ItemCrate load(String path) {
            ItemCrate itemCrate = itemCrateParserStrategy.load(path);
            return new GlobalItemCrate(messages, itemCrate);
        }
    }

}

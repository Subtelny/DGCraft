package pl.subtelny.islands.module.crates.type.search.loader;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.loader.CratePrototypeLoaderStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.basic.BasicItemCrateParserStrategy;
import pl.subtelny.crate.api.type.personal.PersonalItemCrateStrategy;
import pl.subtelny.islands.island.message.IslandMessages;
import pl.subtelny.islands.module.crates.type.search.SearchCratePrototype;
import pl.subtelny.islands.module.crates.type.search.parser.SearchCratePrototypeParserStrategy;

import java.io.File;

@Component
public class LoaderCratePrototypeLoaderStrategy implements CratePrototypeLoaderStrategy {

    private final IslandMessages islandMessages;

    @Autowired
    public LoaderCratePrototypeLoaderStrategy(IslandMessages islandMessages) {
        this.islandMessages = islandMessages;
    }

    @Override
    public CratePrototype load(CratePrototypeLoadRequest request) {
        File file = request.getFile();
        ItemCrateParserStrategy itemCrateStrategy = getItemCrateParserStrategy(request);
        return new SearchCratePrototypeParserStrategy(file, request.getPlugin(), request.getCrateKeyPrefix(), itemCrateStrategy).load("");
    }

    @Override
    public CrateType getType() {
        return SearchCratePrototype.TYPE;
    }

    private ItemCrateParserStrategy getItemCrateParserStrategy(CratePrototypeLoadRequest request) {
        BasicItemCrateParserStrategy basicItemCrate = getBasicItemCrate(request);
        return new PersonalItemCrateStrategy(islandMessages, basicItemCrate);
    }

    private BasicItemCrateParserStrategy getBasicItemCrate(CratePrototypeLoadRequest request) {
        return new BasicItemCrateParserStrategy(
                request.getFile(),
                request.getCostConditionFileParserStrategy(),
                request.getConditionFileParserStrategy(),
                request.getRewardFileParserStrategy()
        );
    }

}

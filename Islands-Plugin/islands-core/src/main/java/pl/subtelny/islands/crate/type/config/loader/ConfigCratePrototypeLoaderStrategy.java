package pl.subtelny.islands.crate.type.config.loader;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.loader.CratePrototypeLoaderStrategy;
import pl.subtelny.crate.api.parser.BasicItemCrateParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.personal.PersonalItemCrateStrategy;
import pl.subtelny.islands.crate.type.config.ConfigCratePrototype;
import pl.subtelny.islands.crate.type.config.parser.ConfigCratePrototypeParserStrategy;
import pl.subtelny.islands.message.IslandMessages;

import java.io.File;

@Component
public class ConfigCratePrototypeLoaderStrategy implements CratePrototypeLoaderStrategy {

    @Override
    public CratePrototype load(CratePrototypeLoadRequest request) {
        File file = request.getFile();
        ItemCrateParserStrategy itemCrateStrategy = getItemCrateParserStrategy(request);
        return new ConfigCratePrototypeParserStrategy(file, request.getPlugin(), request.getCrateKeyPrefix(), itemCrateStrategy).load("");
    }

    @Override
    public CrateType getType() {
        return ConfigCratePrototype.TYPE;
    }

    private ItemCrateParserStrategy getItemCrateParserStrategy(CratePrototypeLoadRequest request) {
        BasicItemCrateParserStrategy basicItemCrate = getBasicItemCrate(request);
        return new PersonalItemCrateStrategy(IslandMessages.get(), basicItemCrate);
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

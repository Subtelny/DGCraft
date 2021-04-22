package pl.subtelny.crate.type.personal.loader;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.loader.CratePrototypeLoaderStrategy;
import pl.subtelny.crate.api.type.personal.PersonalItemCrateStrategy;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.crate.api.type.basic.BasicItemCrateParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.personal.PersonalItemCrate;

import java.io.File;

@Component
public class PersonalCratePrototypeLoaderStrategy implements CratePrototypeLoaderStrategy {

    private final CrateMessages messages;

    @Autowired
    public PersonalCratePrototypeLoaderStrategy(CrateMessages messages) {
        this.messages = messages;
    }

    @Override
    public CratePrototype load(CratePrototypeLoadRequest request) {
        File file = request.getFile();
        ItemCrateParserStrategy itemCrateStrategy = getItemCrateParserStrategy(request);
        return new PersonalCratePrototypeParserStrategy(file, request.getCrateKeyPrefix(), itemCrateStrategy).load("");
    }

    @Override
    public CrateType getType() {
        return PersonalItemCrate.TYPE;
    }

    private ItemCrateParserStrategy getItemCrateParserStrategy(CratePrototypeLoadRequest request) {
        BasicItemCrateParserStrategy basicItemCrate = getBasicItemCrate(request);
        return new PersonalItemCrateStrategy(messages, basicItemCrate);
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

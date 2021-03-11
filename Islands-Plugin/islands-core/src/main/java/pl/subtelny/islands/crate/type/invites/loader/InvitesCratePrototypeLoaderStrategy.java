package pl.subtelny.islands.crate.type.invites.loader;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.loader.CratePrototypeLoaderStrategy;
import pl.subtelny.crate.api.parser.BasicItemCrateParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.personal.PersonalItemCrate;
import pl.subtelny.crate.api.type.personal.PersonalItemCrateStrategy;
import pl.subtelny.islands.crate.type.invites.InvitesCratePrototype;
import pl.subtelny.islands.crate.type.invites.parser.InvitesCratePrototypeParserStrategy;
import pl.subtelny.islands.message.IslandMessages;

import java.io.File;

@Component
public class InvitesCratePrototypeLoaderStrategy implements CratePrototypeLoaderStrategy {

    private final IslandMessages islandMessages;

    @Autowired
    public InvitesCratePrototypeLoaderStrategy(IslandMessages islandMessages) {
        this.islandMessages = islandMessages;
    }

    @Override
    public CratePrototype load(CratePrototypeLoadRequest request) {
        File file = request.getFile();
        ItemCrateParserStrategy itemCrateStrategy = getItemCrateParserStrategy(request);
        return new InvitesCratePrototypeParserStrategy(file, request.getPlugin(), request.getCrateKeyPrefix(), itemCrateStrategy).load("");
    }

    @Override
    public CrateType getType() {
        return InvitesCratePrototype.TYPE;
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

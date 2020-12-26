package pl.subtelny.islands.island.skyblockisland.crate.search.prototype;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.condition.GlobalConditionStrategies;
import pl.subtelny.core.api.condition.GlobalRewardStrategies;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.ACratePrototypeCreator;
import pl.subtelny.crate.api.prototype.CratePrototypeFileParserStrategy;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.islands.Islands;

import java.io.File;

@Component
public class IslandSearchCratePrototypeCreator extends ACratePrototypeCreator {

    @Autowired
    public IslandSearchCratePrototypeCreator(GlobalConditionStrategies conditionStrategies,
                                       GlobalRewardStrategies rewardStrategies) {
        super(conditionStrategies, rewardStrategies);
    }

    @Override
    protected CratePrototypeFileParserStrategy getStrategy(GetCratePrototypeRequest request) {
        File file = request.getFile();
        return new CratePrototypeFileParserStrategy(file,
                getConditionStrategy(file, request.getConditionParsers()),
                getCostConditionStrategy(file, request.getCostConditionParsers()),
                getRewardParsers(file, request.getRewardParsers()),
                Islands.plugin);
    }

    @Override
    public CrateType getType() {
        return IslandSearchCratePrototype.SEARCH_CRATE_TYPE;
    }
}

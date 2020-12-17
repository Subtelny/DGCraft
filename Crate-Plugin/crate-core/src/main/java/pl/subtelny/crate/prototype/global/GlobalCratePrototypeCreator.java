package pl.subtelny.crate.prototype.global;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.condition.GlobalConditionStrategies;
import pl.subtelny.core.api.condition.GlobalRewardStrategies;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.crate.model.crate.global.GlobalCrate;
import pl.subtelny.crate.prototype.ACratePrototypeCreator;
import pl.subtelny.crate.prototype.CratePrototypeFileParserStrategy;

import java.io.File;

@Component
public class GlobalCratePrototypeCreator extends ACratePrototypeCreator {

    @Autowired
    public GlobalCratePrototypeCreator(GlobalConditionStrategies conditionStrategies,
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
                Crate.plugin);
    }

    @Override
    public CrateType getType() {
        return GlobalCrate.GLOBAL_TYPE;
    }
}

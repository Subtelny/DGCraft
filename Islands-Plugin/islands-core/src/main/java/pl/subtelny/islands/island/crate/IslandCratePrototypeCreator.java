package pl.subtelny.islands.island.crate;

import pl.subtelny.core.api.condition.GlobalConditionStrategies;
import pl.subtelny.core.api.condition.GlobalRewardStrategies;
import pl.subtelny.crate.api.prototype.ACratePrototypeCreator;

public abstract class IslandCratePrototypeCreator extends ACratePrototypeCreator {

    public IslandCratePrototypeCreator(GlobalConditionStrategies conditionStrategies,
                                       GlobalRewardStrategies rewardStrategies) {
        super(conditionStrategies, rewardStrategies);
    }
}

package pl.subtelny.crate.prototype;

import pl.subtelny.core.api.condition.GlobalConditionStrategies;
import pl.subtelny.core.api.condition.GlobalRewardStrategies;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeCreator;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ACratePrototypeCreator implements CratePrototypeCreator {

    private final GlobalConditionStrategies conditionStrategies;

    private final GlobalRewardStrategies rewardStrategies;

    public ACratePrototypeCreator(GlobalConditionStrategies conditionStrategies, GlobalRewardStrategies rewardStrategies) {
        this.conditionStrategies = conditionStrategies;
        this.rewardStrategies = rewardStrategies;
    }

    @Override
    public CratePrototype createPrototype(GetCratePrototypeRequest request) {
        return getStrategy(request).load("");
    }

    protected abstract CratePrototypeFileParserStrategy getStrategy(GetCratePrototypeRequest request);

    protected List<PathAbstractFileParserStrategy<? extends Condition>> getConditionStrategy(
            File file,
            List<PathAbstractFileParserStrategy<? extends Condition>> conditions) {
        return Stream.concat(
                conditions.stream(),
                conditionStrategies.getGlobalConditionStrategies(file).stream()
        ).collect(Collectors.toList());
    }

    protected List<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditionStrategy(
            File file,
            List<PathAbstractFileParserStrategy<? extends CostCondition>> conditions) {
        return Stream.concat(
                conditions.stream(),
                conditionStrategies.getGlobalCostConditionStrategies(file).stream()
        ).collect(Collectors.toList());
    }

    protected List<PathAbstractFileParserStrategy<? extends Reward>> getRewardParsers(
            File file,
            List<PathAbstractFileParserStrategy<? extends Reward>> rewards) {
        return Stream.concat(
                rewards.stream(),
                rewardStrategies.getRewardParsers(file).stream()
        ).collect(Collectors.toList());
    }

}

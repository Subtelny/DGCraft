package pl.subtelny.crate.api.item;

import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.List;

public class ItemCrateLoadRequest {

    private final File file;

    private final List<ItemCrateWrapperParserStrategy> itemCrateWrapperParserStrategies;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    public ItemCrateLoadRequest(File file, List<ItemCrateWrapperParserStrategy> itemCrateWrapperParserStrategies,
                                List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        this.file = file;
        this.itemCrateWrapperParserStrategies = itemCrateWrapperParserStrategies;
        this.rewardParsers = rewardParsers;
        this.costConditionParsers = costConditionParsers;
        this.conditionParsers = conditionParsers;
    }

    public File getFile() {
        return file;
    }

    public List<ItemCrateWrapperParserStrategy> getItemCrateWrapperParserStrategies() {
        return itemCrateWrapperParserStrategies;
    }

    public List<PathAbstractFileParserStrategy<? extends Reward>> getRewardParsers() {
        return rewardParsers;
    }

    public List<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditionParsers() {
        return costConditionParsers;
    }

    public List<PathAbstractFileParserStrategy<? extends Condition>> getConditionParsers() {
        return conditionParsers;
    }
}

package pl.subtelny.crate.api.prototype;

import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrateLoadRequest;
import pl.subtelny.crate.api.item.ItemCrateWrapperParserStrategy;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CratePrototypeLoadGlobalRequest {

    private final File file;

    private final CrateType crateType;

    private final ItemCrateLoadRequest itemCrateLoadRequest;

    public CratePrototypeLoadGlobalRequest(File file,
                                           CrateType crateType,
                                           ItemCrateLoadRequest itemCrateLoadRequest) {
        this.file = file;
        this.crateType = crateType;
        this.itemCrateLoadRequest = itemCrateLoadRequest;
    }

    public File getFile() {
        return file;
    }

    public CrateType getCrateType() {
        return crateType;
    }

    public ItemCrateLoadRequest getItemCrateLoadRequest() {
        return itemCrateLoadRequest;
    }

    public static Builder builder(File file, CrateType crateType) {
        return new Builder(file, crateType);
    }

    public static class Builder {

        private final File file;

        private final CrateType crateType;

        private final List<ItemCrateWrapperParserStrategy> itemCrateWrapperParserStrategies = new ArrayList<>();

        private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = new ArrayList<>();

        private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers = new ArrayList<>();

        private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers = new ArrayList<>();

        public Builder(File file, CrateType crateType) {
            this.file = file;
            this.crateType = crateType;
        }

        public Builder addItemCrateWrapperParserStrategies(List<ItemCrateWrapperParserStrategy> strategies) {
            itemCrateWrapperParserStrategies.addAll(strategies);
            return this;
        }

        public Builder addRewardParsers(List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers) {
            this.rewardParsers.addAll(rewardParsers);
            return this;
        }

        public Builder addCostConditionParsers(List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers) {
            this.costConditionParsers.addAll(costConditionParsers);
            return this;
        }

        public Builder addConditionParsers(List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
            this.conditionParsers.addAll(conditionParsers);
            return this;
        }

        public CratePrototypeLoadGlobalRequest build() {
            ItemCrateLoadRequest itemCrateLoadRequest = new ItemCrateLoadRequest(file, itemCrateWrapperParserStrategies, rewardParsers, costConditionParsers, conditionParsers);
            return new CratePrototypeLoadGlobalRequest(file, crateType, itemCrateLoadRequest);
        }

    }
}

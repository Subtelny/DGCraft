package pl.subtelny.crate.api.prototype;

import pl.subtelny.crate.api.item.ItemCrateLoadRequest;
import pl.subtelny.crate.api.item.ItemCrateWrapperParserStrategy;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CratePrototypeLoadRequest {

    private final File file;

    private final List<CratePrototypeCreator> cratePrototypeCreators;

    private final ItemCrateLoadRequest itemCrateLoadRequest;

    public CratePrototypeLoadRequest(File file,
            List<CratePrototypeCreator> cratePrototypeCreators,
            ItemCrateLoadRequest itemCrateLoadRequest) {
        this.file = file;
        this.cratePrototypeCreators = cratePrototypeCreators;
        this.itemCrateLoadRequest = itemCrateLoadRequest;
    }

    public File getFile() {
        return file;
    }

    public List<CratePrototypeCreator> getCratePrototypeCreators() {
        return cratePrototypeCreators;
    }

    public ItemCrateLoadRequest getItemCrateLoadRequest() {
        return itemCrateLoadRequest;
    }

    public static Builder builder(File file) {
        return new Builder(file);
    }

    public static class Builder {

        private final File file;

        private final List<CratePrototypeCreator> cratePrototypeCreators = new ArrayList<>();

        private final List<ItemCrateWrapperParserStrategy> itemCrateWrapperParserStrategies = new ArrayList<>();

        private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = new ArrayList<>();

        private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers = new ArrayList<>();

        private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers = new ArrayList<>();

        public Builder(File file) {
            this.file = file;
        }

        public Builder addCratePrototypeCreator(CratePrototypeCreator creator) {
            cratePrototypeCreators.add(creator);
            return this;
        }

        public Builder addItemCrateWrapperParserStrategy(ItemCrateWrapperParserStrategy strategy) {
            itemCrateWrapperParserStrategies.add(strategy);
            return this;
        }

        public Builder addRewardParser(PathAbstractFileParserStrategy<? extends Reward> rewardParser) {
            rewardParsers.add(rewardParser);
            return this;
        }

        public Builder addCostConditionParser(PathAbstractFileParserStrategy<? extends CostCondition> costConditionParser) {
            costConditionParsers.add(costConditionParser);
            return this;
        }

        public Builder addConditionParser(PathAbstractFileParserStrategy<? extends Condition> conditionParser) {
            conditionParsers.add(conditionParser);
            return this;
        }

        public CratePrototypeLoadRequest build() {
            ItemCrateLoadRequest itemCrateLoadRequest = new ItemCrateLoadRequest(file, itemCrateWrapperParserStrategies, rewardParsers, costConditionParsers, conditionParsers);
            return new CratePrototypeLoadRequest(file, cratePrototypeCreators, itemCrateLoadRequest);
        }

    }

}

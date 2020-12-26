package pl.subtelny.crate.api.query.request;

import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetCratePrototypeRequest {

    private final File file;

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    public GetCratePrototypeRequest(File file,
                                    List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                    List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                    List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers) {
        this.file = file;
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
        this.rewardParsers = rewardParsers;
    }

    public File getFile() {
        return file;
    }

    public List<PathAbstractFileParserStrategy<? extends Condition>> getConditionParsers() {
        return conditionParsers;
    }

    public List<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditionParsers() {
        return costConditionParsers;
    }

    public List<PathAbstractFileParserStrategy<? extends Reward>> getRewardParsers() {
        return rewardParsers;
    }

    public static Builder builder(File file) {
        return new Builder(file);
    }

    public static class Builder {

        private final File file;

        private List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers = new ArrayList<>();

        private List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers = new ArrayList<>();

        private List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = new ArrayList<>();

        public Builder(File file) {
            this.file = file;
        }

        public Builder conditionParsers(List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
            this.conditionParsers = conditionParsers;
            return this;
        }

        public Builder costConditionParsers(List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers) {
            this.costConditionParsers = costConditionParsers;
            return this;
        }

        public Builder rewardParsers(List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers) {
            this.rewardParsers = rewardParsers;
            return this;
        }

        public GetCratePrototypeRequest build() {
            return new GetCratePrototypeRequest(file, conditionParsers, costConditionParsers, rewardParsers);
        }

    }

}

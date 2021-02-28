package pl.subtelny.crate.loader;

import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.ConditionFileParserStrategy;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.CostConditionFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;
import pl.subtelny.utilities.reward.RewardFileParserStrategy;

import java.io.File;
import java.util.List;
import java.util.Objects;

public final class CratePrototypeLoadRequest {

    private final File file;

    private final String crateKeyPrefix;

    private final ConditionFileParserStrategy conditionFileParserStrategy;

    private final CostConditionFileParserStrategy costConditionFileParserStrategy;

    private final RewardFileParserStrategy rewardFileParserStrategy;

    private CratePrototypeLoadRequest(File file,
                                      String crateKeyPrefix,
                                      ConditionFileParserStrategy conditionFileParserStrategy,
                                      CostConditionFileParserStrategy costConditionFileParserStrategy,
                                      RewardFileParserStrategy rewardFileParserStrategy) {
        this.file = file;
        this.crateKeyPrefix = crateKeyPrefix;
        this.conditionFileParserStrategy = conditionFileParserStrategy;
        this.costConditionFileParserStrategy = costConditionFileParserStrategy;
        this.rewardFileParserStrategy = rewardFileParserStrategy;
    }

    public static CratePrototypeLoadRequest of(File file,
                                               String keyPrefix,
                                               List<PathAbstractFileParserStrategy<? extends Condition>> conditionFileParserStrategies,
                                               List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionFileParserStrategies,
                                               List<PathAbstractFileParserStrategy<? extends Reward>> rewardFileParserStrategies) {
        return new CratePrototypeLoadRequest(
                file,
                keyPrefix,
                new ConditionFileParserStrategy(file, conditionFileParserStrategies),
                new CostConditionFileParserStrategy(file, costConditionFileParserStrategies),
                new RewardFileParserStrategy(file, rewardFileParserStrategies)
        );
    }

    public File getFile() {
        return file;
    }

    public String getCrateKeyPrefix() {
        return crateKeyPrefix;
    }

    public ConditionFileParserStrategy getConditionFileParserStrategy() {
        return conditionFileParserStrategy;
    }

    public CostConditionFileParserStrategy getCostConditionFileParserStrategy() {
        return costConditionFileParserStrategy;
    }

    public RewardFileParserStrategy getRewardFileParserStrategy() {
        return rewardFileParserStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CratePrototypeLoadRequest that = (CratePrototypeLoadRequest) o;
        return Objects.equals(file, that.file) && Objects.equals(crateKeyPrefix, that.crateKeyPrefix) && Objects.equals(conditionFileParserStrategy, that.conditionFileParserStrategy) && Objects.equals(costConditionFileParserStrategy, that.costConditionFileParserStrategy) && Objects.equals(rewardFileParserStrategy, that.rewardFileParserStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, crateKeyPrefix, conditionFileParserStrategy, costConditionFileParserStrategy, rewardFileParserStrategy);
    }
}

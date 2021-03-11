package pl.subtelny.crate.service;

import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RegisterCratePrototypeRequest {

    private final File file;

    private final Plugin plugin;

    private final String crateKeyPrefix;

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionFileParserStrategies;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionFileParserStrategies;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardFileParserStrategies;

    public RegisterCratePrototypeRequest(File file,
                                         Plugin plugin,
                                         String crateKeyPrefix,
                                         List<PathAbstractFileParserStrategy<? extends Condition>> conditionFileParserStrategies,
                                         List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionFileParserStrategies,
                                         List<PathAbstractFileParserStrategy<? extends Reward>> rewardFileParserStrategies) {
        this.file = file;
        this.plugin = plugin;
        this.crateKeyPrefix = crateKeyPrefix;
        this.conditionFileParserStrategies = conditionFileParserStrategies;
        this.costConditionFileParserStrategies = costConditionFileParserStrategies;
        this.rewardFileParserStrategies = rewardFileParserStrategies;
    }


    public static RegisterCratePrototypeRequest of(File file,
                                                   Plugin plugin,
                                                   String keyPrefix,
                                                   List<PathAbstractFileParserStrategy<? extends Condition>> conditionFileParserStrategies,
                                                   List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionFileParserStrategies,
                                                   List<PathAbstractFileParserStrategy<? extends Reward>> rewardFileParserStrategies) {
        return new RegisterCratePrototypeRequest(
                file,
                plugin, keyPrefix,
                conditionFileParserStrategies,
                costConditionFileParserStrategies,
                rewardFileParserStrategies
        );
    }

    public static RegisterCratePrototypeRequest of(File file, Plugin plugin, String keyPrefix) {
        return of(file, plugin, keyPrefix, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public static RegisterCratePrototypeRequest of(File file, Plugin plugin) {
        return of(file, plugin, null);
    }

    public File getFile() {
        return file;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getCrateKeyPrefix() {
        return crateKeyPrefix;
    }

    public List<PathAbstractFileParserStrategy<? extends Condition>> getConditionFileParserStrategies() {
        return conditionFileParserStrategies;
    }

    public List<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditionFileParserStrategies() {
        return costConditionFileParserStrategies;
    }

    public List<PathAbstractFileParserStrategy<? extends Reward>> getRewardFileParserStrategies() {
        return rewardFileParserStrategies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterCratePrototypeRequest that = (RegisterCratePrototypeRequest) o;
        return Objects.equals(file, that.file) && Objects.equals(plugin, that.plugin) && Objects.equals(crateKeyPrefix, that.crateKeyPrefix) && Objects.equals(conditionFileParserStrategies, that.conditionFileParserStrategies) && Objects.equals(costConditionFileParserStrategies, that.costConditionFileParserStrategies) && Objects.equals(rewardFileParserStrategies, that.rewardFileParserStrategies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, plugin, crateKeyPrefix, conditionFileParserStrategies, costConditionFileParserStrategies, rewardFileParserStrategies);
    }
}

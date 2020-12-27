package pl.subtelny.crate.api.query.request;

import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetCratePrototypeRequest {

    private final Plugin plugin;

    private final File file;

    private final String cratePrefix;

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    public GetCratePrototypeRequest(Plugin plugin,
                                    File file,
                                    String cratePrefix, List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                    List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                    List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers) {
        this.plugin = plugin;
        this.file = file;
        this.cratePrefix = cratePrefix;
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
        this.rewardParsers = rewardParsers;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public File getFile() {
        return file;
    }

    public String getCratePrefix() {
        return cratePrefix;
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

    public static Builder builder(Plugin plugin, File file) {
        return new Builder(plugin, file);
    }

    public static class Builder {

        private final Plugin plugin;

        private final File file;

        private String cratePrefix;

        private List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers = new ArrayList<>();

        private List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers = new ArrayList<>();

        private List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = new ArrayList<>();

        public Builder(Plugin plugin, File file) {
            this.plugin = plugin;
            this.file = file;
        }

        public Builder cratePrefix(String cratePrefix) {
            this.cratePrefix = cratePrefix;
            return this;
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
            return new GetCratePrototypeRequest(plugin, file, cratePrefix, conditionParsers, costConditionParsers, rewardParsers);
        }

    }

}

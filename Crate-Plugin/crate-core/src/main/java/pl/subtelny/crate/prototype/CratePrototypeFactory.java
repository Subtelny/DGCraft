package pl.subtelny.crate.prototype;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.condition.GlobalConditionStrategies;
import pl.subtelny.core.api.condition.GlobalRewardStrategies;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.model.crate.page.PageCrate;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.List;

@Component
public class CratePrototypeFactory {

    private final GlobalConditionStrategies conditionStrategies;

    private final GlobalRewardStrategies rewardStrategies;

    @Autowired
    public CratePrototypeFactory(GlobalConditionStrategies conditionStrategies, GlobalRewardStrategies rewardStrategies) {
        this.conditionStrategies = conditionStrategies;
        this.rewardStrategies = rewardStrategies;
    }

    public CratePrototype createPrototype(File file) {
        CrateType type = getType(file);
        CratePrototypeFileParserStrategy strategy = getCreator(type, file);
        return strategy.load("");
    }

    private CratePrototypeFileParserStrategy getCreator(CrateType crateType, File file) {
        List<PathAbstractFileParserStrategy<? extends Condition>> conditionStrategy = conditionStrategies.getGlobalConditionStrategies(file);
        List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionStrategies = conditionStrategies.getGlobalCostConditionStrategies(file);
        List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = rewardStrategies.getRewardParsers(file);

        if (PageCrate.PAGE_TYPE.equals(crateType)) {
            return new PageCratePrototypeFileParserStrategy(file, conditionStrategy, costConditionStrategies, rewardParsers, Crate.plugin);
        }
        return new CratePrototypeFileParserStrategy(file, conditionStrategy, costConditionStrategies, rewardParsers, Crate.plugin);
    }

    private CrateType getType(File file) {
        String type = YamlConfiguration.loadConfiguration(file).getString("type");
        return new CrateType(type);
    }

}

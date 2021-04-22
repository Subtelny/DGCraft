package pl.subtelny.crate.type;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.item.BaseItemCrate;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseItemCrateFileParserStrategy extends AbstractFileParserStrategy<BaseItemCrate> {

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    public BaseItemCrateFileParserStrategy(YamlConfiguration configuration,
                                           File file,
                                           List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                           List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                           List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        super(configuration, file);
        this.rewardParsers = rewardParsers;
        this.costConditionParsers = costConditionParsers;
        this.conditionParsers = conditionParsers;
    }

    public BaseItemCrateFileParserStrategy(File file,
                                           List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                           List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                           List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        super(file);
        this.rewardParsers = rewardParsers;
        this.costConditionParsers = costConditionParsers;
        this.conditionParsers = conditionParsers;
    }

    @Override
    public BaseItemCrate load(String path) {
        ItemStackFileParserStrategy itemStackFileParserStrategy = new ItemStackFileParserStrategy(configuration, file);
        ItemStack itemStack = itemStackFileParserStrategy.load(path);
        List<Condition> conditions = loadConditions(path);
        List<CostCondition> costConditions = loadCostConditions(path);
        List<Reward> rewards = loadRewards(path);
        boolean closeAfterClick = configuration.getBoolean(path + ".close-after-click", false);
        return new BaseItemCrate(itemStack, conditions, costConditions, rewards, closeAfterClick);
    }

    private List<Reward> loadRewards(String path) {
        String rewardsPath = path + ". on-click.rewards";
        return getStringStream(rewardsPath)
                .map(key -> loadReward(rewardsPath + "." + key))
                .collect(Collectors.toList());
    }

    private Reward loadReward(String path) {
        return rewardParsers.stream()
                .filter(strategy -> configuration.contains(path + "." + strategy.getPath()))
                .findFirst()
                .map(strategy -> strategy.load(path))
                .orElseThrow(() -> new IllegalStateException("Not found reward for path " + path + " - " + file.getName()));
    }

    private List<CostCondition> loadCostConditions(String path) {
        String costConditionsPath = path + ". on-click.cost-conditions";
        return getStringStream(costConditionsPath)
                .map(key -> loadCostCondition(costConditionsPath + "." + key))
                .collect(Collectors.toList());
    }

    private CostCondition loadCostCondition(String path) {
        return costConditionParsers.stream()
                .filter(strategy -> configuration.contains(path + "." + strategy.getPath()))
                .findFirst()
                .map(strategy -> strategy.load(path))
                .orElseThrow(() -> new IllegalStateException("Not found cost-condition for path " + path + " - " + file.getName()));
    }

    private List<Condition> loadConditions(String path) {
        String conditionsPath = path + ". on-click.conditions";
        return getStringStream(conditionsPath)
                .map(key -> loadCondition(conditionsPath + "." + key))
                .collect(Collectors.toList());
    }

    private Condition loadCondition(String path) {
        return conditionParsers.stream()
                .filter(strategy -> configuration.contains(path + "." + strategy.getPath()))
                .findFirst()
                .map(strategy -> strategy.load(path))
                .orElseThrow(() -> new IllegalStateException("Not found condition for path " + path + " - " + file.getName()));
    }

    private Stream<String> getStringStream(String path) {
        return ConfigUtil.getSectionKeys(configuration, path)
                .stream()
                .flatMap(Collection::stream);
    }

    @Override
    public Saveable set(String path, BaseItemCrate value) {
        return null;
    }

}

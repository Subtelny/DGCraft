package pl.subtelny.crate.api.prototype;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.*;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;
import pl.subtelny.utilities.reward.RewardFileParserStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemCratePrototypeFileParserStrategy extends AbstractFileParserStrategy<ItemCratePrototype> {

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    public ItemCratePrototypeFileParserStrategy(YamlConfiguration configuration,
                                                File file,
                                                List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                                List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
        this.rewardParsers = rewardParsers;
    }

    @Override
    public ItemCratePrototype load(String path) {
        ItemStack itemStack = new ItemStackFileParserStrategy(configuration, file).load(path);
        boolean movable = configuration.getBoolean(path + ".movable");
        boolean closeAfterClick = configuration.getBoolean(path + ".close-after-click");
        List<Condition> conditions = getConditions(path + ".on-click.conditions");
        List<CostCondition> costConditions = getCostConditions(path + ".on-click.conditions");
        List<Reward> rewards = getRewards(path + ".on-click.rewards");
        return new ItemCratePrototype(itemStack, movable, closeAfterClick, conditions, costConditions, rewards);
    }

    private List<Reward> getRewards(String path) {
        RewardFileParserStrategy strategy = new RewardFileParserStrategy(configuration, file, rewardParsers);
        return ConfigUtil.getSectionKeys(configuration, path)
                .map(strings -> getRewards(strategy, path, strings))
                .orElseGet(ArrayList::new);
    }

    private List<Reward> getRewards(RewardFileParserStrategy strategy, String path, Set<String> sectionPaths) {
        return sectionPaths.stream()
                .map(sectionPath -> strategy.load(path + "." + sectionPath))
                .collect(Collectors.toList());
    }

    private List<CostCondition> getCostConditions(String path) {
        CostConditionFileParserStrategy strategy = new CostConditionFileParserStrategy(configuration, file, costConditionParsers);
        return ConditionUtil.findCostConditionPaths(configuration, path).stream()
                .map(strategy::load)
                .collect(Collectors.toList());
    }

    private List<Condition> getConditions(String path) {
        ConditionFileParserStrategy strategy = new ConditionFileParserStrategy(configuration, file, conditionParsers);
        return ConditionUtil.findConditionPaths(configuration, path).stream()
                .map(strategy::load)
                .collect(Collectors.toList());
    }

    @Override
    public Saveable set(String path, ItemCratePrototype value) {
        throw new UnsupportedOperationException("Saving ItemCratePrototype is not implemented yet");
    }
}

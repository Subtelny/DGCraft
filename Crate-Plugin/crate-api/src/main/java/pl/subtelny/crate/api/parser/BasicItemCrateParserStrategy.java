package pl.subtelny.crate.api.parser;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.type.basic.BasicItemCrate;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.ConditionFileParserStrategy;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.CostConditionFileParserStrategy;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;
import pl.subtelny.utilities.reward.RewardFileParserStrategy;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicItemCrateParserStrategy extends AbstractFileParserStrategy<ItemCrate> implements ItemCrateParserStrategy {

    private final CostConditionFileParserStrategy costConditionFileParserStrategy;

    private final ConditionFileParserStrategy conditionFileParserStrategy;

    private final RewardFileParserStrategy rewardFileParserStrategy;

    public BasicItemCrateParserStrategy(File file,
                                        CostConditionFileParserStrategy costConditionFileParserStrategy,
                                        ConditionFileParserStrategy conditionFileParserStrategy,
                                        RewardFileParserStrategy rewardFileParserStrategy) {
        super(file);
        this.costConditionFileParserStrategy = costConditionFileParserStrategy;
        this.conditionFileParserStrategy = conditionFileParserStrategy;
        this.rewardFileParserStrategy = rewardFileParserStrategy;
    }

    @Override
    public ItemCrate load(String path) {
        ItemStack itemStack = getItemStack(path);
        boolean movable = configuration.getBoolean(path + ".movable");
        String onClickPath = path + ".on-click";
        List<Reward> rewards = loadRewards(onClickPath + ".rewards");
        List<Condition> conditions = loadConditions(onClickPath + ".conditions");
        List<CostCondition> costConditions = loadCostConditions(onClickPath + ".cost-conditions");
        return new BasicItemCrate(itemStack, conditions, costConditions, rewards, movable);
    }

    private List<Reward> loadRewards(String path) {
        Set<String> conditionPaths = ConfigUtil.getSectionKeys(configuration, path).orElseGet(HashSet::new);
        return conditionPaths.stream()
                .map(rewardPath -> rewardFileParserStrategy.load(path + "." + rewardPath))
                .collect(Collectors.toList());
    }

    private List<CostCondition> loadCostConditions(String path) {
        Set<String> conditionPaths = ConfigUtil.getSectionKeys(configuration, path).orElseGet(HashSet::new);
        return conditionPaths.stream()
                .map(conditionPath -> costConditionFileParserStrategy.load(path + "." + conditionPath))
                .collect(Collectors.toList());
    }

    private List<Condition> loadConditions(String path) {
        Set<String> conditionPaths = ConfigUtil.getSectionKeys(configuration, path).orElseGet(HashSet::new);
        return conditionPaths.stream()
                .map(conditionPath -> conditionFileParserStrategy.load(path + "." + conditionPath))
                .collect(Collectors.toList());
    }

    private ItemStack getItemStack(String path) {
        return new ItemStackFileParserStrategy(configuration, file).load(path);
    }

    @Override
    public Saveable set(String path, ItemCrate value) {
        throw new UnsupportedOperationException("Saving ItemCrate is not implemented yet");
    }
}

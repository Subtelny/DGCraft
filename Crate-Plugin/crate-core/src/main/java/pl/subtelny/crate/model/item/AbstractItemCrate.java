package pl.subtelny.crate.model.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.reward.Reward;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractItemCrate implements ItemCrate {

    private final ItemStack itemStack;

    private final ItemCratePrototype prototype;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    private final List<Reward> rewards;

    public AbstractItemCrate(ItemStack itemStack,
                             ItemCratePrototype prototype,
                             List<Condition> conditions,
                             List<CostCondition> costConditions,
                             List<Reward> rewards) {
        this.itemStack = itemStack;
        this.prototype = prototype;
        this.conditions = conditions;
        this.costConditions = costConditions;
        this.rewards = rewards;
    }

    @Override
    public ItemCrateClickResult click(Player player) {
        return null;
    }

    protected void satisfyItemCrate(Player player) {
        costConditions.forEach(costCondition -> costCondition.satisfyCondition(player));
        rewards.forEach(reward -> reward.admitReward(player));
    }

    protected List<Condition> getNotSatisfiedConditions(Player player) {
        return Stream.of(conditions, costConditions)
                .flatMap(Collection::stream)
                .filter(condition -> !condition.satisfiesCondition(player))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isMovable() {
        return prototype.isMovable();
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
}

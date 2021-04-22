package pl.subtelny.crate.api.type.basic;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.ItemCrateClickResult;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.reward.Reward;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicItemCrate implements ItemCrate {

    private final ItemStack baseItemStack;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    private final List<Reward> rewards;

    private final boolean movable;

    public BasicItemCrate(ItemStack baseItemStack,
                          List<Condition> conditions,
                          List<CostCondition> costConditions,
                          List<Reward> rewards,
                          boolean movable) {
        this.baseItemStack = baseItemStack;
        this.conditions = conditions;
        this.costConditions = costConditions;
        this.rewards = rewards;
        this.movable = movable;
    }

    @Override
    public ItemCrateClickResult click(Player player, CrateData crateData) {
        List<Condition> notSatisfiedConditions = getNotSatisfiedConditions(player);
        if (notSatisfiedConditions.isEmpty()) {
            admitRewards(player, rewards);
            return ItemCrateClickResult.success(movable);
        }
        return ItemCrateClickResult.failure(notSatisfiedConditions);
    }

    @Override
    public ItemStack getItemStack() {
        return baseItemStack.clone();
    }

    private void admitRewards(Player player, List<Reward> rewards) {
        rewards.forEach(reward -> reward.admitReward(player));
    }

    private List<Condition> getNotSatisfiedConditions(Player player) {
        return Stream.concat(conditions.stream(), costConditions.stream())
                .filter(condition -> !condition.satisfiesCondition(player))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicItemCrate that = (BasicItemCrate) o;
        return movable == that.movable && Objects.equals(baseItemStack, that.baseItemStack) && Objects.equals(conditions, that.conditions) && Objects.equals(costConditions, that.costConditions) && Objects.equals(rewards, that.rewards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseItemStack, conditions, costConditions, rewards, movable);
    }
}

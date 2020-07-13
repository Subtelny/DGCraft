package pl.subtelny.gui.api.crate.model;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.reward.Reward;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCrate {

    private final ItemStack originalItemStack;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    private final List<Reward> rewards;

    private final CrateId crateToOpen;

    public ItemCrate(ItemStack originalItemStack,
                     List<Condition> conditions,
                     List<CostCondition> costConditions,
                     List<Reward> rewards,
                     @Nullable CrateId crateToOpen) {
        this.originalItemStack = originalItemStack;
        this.conditions = conditions;
        this.costConditions = costConditions;
        this.rewards = rewards;
        this.crateToOpen = crateToOpen;
    }

    public void rewardPlayer(Player player) {
        getRewards().forEach(reward -> reward.admitReward(player));
    }

    public void satisfyCostConditions(Player player) {
        getCostConditions().forEach(costCondition -> costCondition.satisfyCondition(player));
    }

    public boolean satisfiesAllConditions(Player player) {
        List<Condition> notSatisfiedConditions = getNotStatisfiedConditions(player);
        return notSatisfiedConditions.isEmpty();
    }

    public List<Condition> getNotStatisfiedConditions(Player player) {
        return Stream.concat(getConditions().stream(), getCostConditions().stream())
                .filter(condition -> !condition.satisfiesCondition(player))
                .collect(Collectors.toList());
    }

    public ItemStack getOriginalItemStack() {
        return originalItemStack;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<CostCondition> getCostConditions() {
        return costConditions;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public Optional<CrateId> getCrateToOpen() {
        return Optional.ofNullable(crateToOpen);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCrate itemCrate = (ItemCrate) o;
        return Objects.equals(originalItemStack, itemCrate.originalItemStack) &&
                Objects.equals(conditions, itemCrate.conditions) &&
                Objects.equals(costConditions, itemCrate.costConditions) &&
                Objects.equals(rewards, itemCrate.rewards) &&
                Objects.equals(crateToOpen, itemCrate.crateToOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalItemStack, conditions, costConditions, rewards, crateToOpen);
    }
}

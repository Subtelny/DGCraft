package pl.subtelny.gui.crate.model;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.gui.api.crate.model.CrateId;
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

    private final boolean closeAfterClick;

    public ItemCrate(ItemStack originalItemStack,
                     List<Condition> conditions,
                     List<CostCondition> costConditions,
                     List<Reward> rewards,
                     boolean closeAfterClick) {
        this.originalItemStack = originalItemStack;
        this.conditions = conditions;
        this.costConditions = costConditions;
        this.rewards = rewards;
        this.closeAfterClick = closeAfterClick;
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

    public boolean isCloseAfterClick() {
        return closeAfterClick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCrate itemCrate = (ItemCrate) o;
        return closeAfterClick == itemCrate.closeAfterClick &&
                Objects.equals(originalItemStack, itemCrate.originalItemStack) &&
                Objects.equals(conditions, itemCrate.conditions) &&
                Objects.equals(costConditions, itemCrate.costConditions) &&
                Objects.equals(rewards, itemCrate.rewards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalItemStack, conditions, costConditions, rewards, closeAfterClick);
    }
}

package pl.subtelny.crate.api.prototype;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.reward.Reward;

import java.util.List;

public class ItemCratePrototype {

    private final ItemStack itemStack;

    private final boolean movable;

    private final boolean closeAfterClick;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    private final List<Reward> rewards;

    public ItemCratePrototype(ItemStack itemStack,
                              boolean movable,
                              boolean closeAfterClick,
                              List<Condition> conditions,
                              List<CostCondition> costConditions,
                              List<Reward> rewards) {
        this.itemStack = itemStack;
        this.movable = movable;
        this.closeAfterClick = closeAfterClick;
        this.conditions = conditions;
        this.costConditions = costConditions;
        this.rewards = rewards;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public boolean isMovable() {
        return movable;
    }

    public boolean isCloseAfterClick() {
        return closeAfterClick;
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
}

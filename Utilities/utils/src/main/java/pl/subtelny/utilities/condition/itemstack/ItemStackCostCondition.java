package pl.subtelny.utilities.condition.itemstack;

import org.bukkit.entity.Player;
import pl.subtelny.utilities.condition.CostCondition;

public class ItemStackCostCondition extends ItemStackCondition implements CostCondition {

    public ItemStackCostCondition(ItemStackCondition condition) {
        super(condition.itemStack);
    }

    @Override
    public void satisfyCondition(Player player) {
        player.getInventory().remove(itemStack);
    }
}

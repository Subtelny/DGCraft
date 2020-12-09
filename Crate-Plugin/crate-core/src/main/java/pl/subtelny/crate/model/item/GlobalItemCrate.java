package pl.subtelny.crate.model.item;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.reward.Reward;

import java.util.List;

public class GlobalItemCrate extends AbstractItemCrate {

    public GlobalItemCrate(ItemStack itemStack,
                           ItemCratePrototype prototype,
                           List<Condition> conditions,
                           List<CostCondition> costConditions,
                           List<Reward> rewards) {
        super(itemStack, prototype, conditions, costConditions, rewards);
    }

}

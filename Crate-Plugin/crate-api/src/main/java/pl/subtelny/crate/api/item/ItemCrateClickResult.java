package pl.subtelny.crate.api.item;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.condition.Condition;

import java.util.Collections;
import java.util.List;

public class ItemCrateClickResult {

    public final static ItemCrateClickResult SUCCESS = new ItemCrateClickResult(null, false, false, Collections.emptyList());

    public final static ItemCrateClickResult CLOSE_CRATE = new ItemCrateClickResult(null, false, true, Collections.emptyList());

    private final ItemStack newItemStack;

    private final boolean updateItemStack;

    private final boolean closeCrate;

    private final List<Condition> notSatisfiedConditions;

    public ItemCrateClickResult(ItemStack newItemStack, boolean updateItemStack, boolean closeCrate, List<Condition> notSatisfiedConditions) {
        this.newItemStack = newItemStack;
        this.updateItemStack = updateItemStack;
        this.notSatisfiedConditions = notSatisfiedConditions;
        this.closeCrate = closeCrate;
    }

    public ItemStack getNewItemStack() {
        return newItemStack;
    }

    public boolean isUpdateItemStack() {
        return updateItemStack;
    }

    public boolean isSuccess() {
        return notSatisfiedConditions.isEmpty();
    }

    public List<Condition> getNotSatisfiedConditions() {
        return notSatisfiedConditions;
    }

    public boolean isCloseCrate() {
        return closeCrate;
    }
}

package pl.subtelny.crate;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.condition.Condition;

import java.util.Collections;
import java.util.List;

public final class ItemCrateClickResult {

    private static final ItemCrateClickResult SUCCESS_NOT_MOVABLE = success(null, false);

    private static final ItemCrateClickResult SUCCESS_MOVABLE = success(null, true);

    private final ItemStack itemStack;

    private final List<Condition> notSatisfiedConditions;

    private final boolean removeItem;

    private final boolean success;

    private final boolean movable;

    private ItemCrateClickResult(ItemStack itemStack,
                                 List<Condition> notSatisfiedConditions,
                                 boolean removeItem,
                                 boolean success,
                                 boolean movable) {
        this.movable = movable;
        Validation.isFalse(!removeItem && itemStack == null, "ItemStack cannot be null when removeItem is false");
        this.success = success;
        this.itemStack = itemStack;
        this.notSatisfiedConditions = notSatisfiedConditions;
        this.removeItem = removeItem;
    }

    public static ItemCrateClickResult success(boolean movable) {
        return movable ? SUCCESS_MOVABLE : SUCCESS_NOT_MOVABLE;
    }

    public static ItemCrateClickResult failure(ItemStack newItemStack, List<Condition> notSatisfiedConditions) {
        return new ItemCrateClickResult(newItemStack, notSatisfiedConditions, false, false, false);
    }

    public static ItemCrateClickResult failure(List<Condition> notSatisfiedConditions) {
        return new ItemCrateClickResult(null, notSatisfiedConditions, false, false, false);
    }

    public static ItemCrateClickResult success(ItemStack newItemStack, boolean movable) {
        return new ItemCrateClickResult(newItemStack, Collections.emptyList(), false, true, movable);
    }

    public static ItemCrateClickResult result(ItemStack newItemStack, ItemCrateClickResult result) {
        if (result.isSuccess()) {
            return success(newItemStack, result.isMovable());
        } else {
            return failure(newItemStack, result.getNotSatisfiedConditions());
        }
    }

    public boolean isMovable() {
        return movable;
    }

    public boolean isRemoveItem() {
        return removeItem;
    }

    public ItemStack getNewItemStack() {
        return itemStack;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Condition> getNotSatisfiedConditions() {
        return notSatisfiedConditions;
    }
}

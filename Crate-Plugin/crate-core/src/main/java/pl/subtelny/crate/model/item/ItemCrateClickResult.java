package pl.subtelny.crate.model.item;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.condition.Condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ItemCrateClickResult {

    public static final ItemCrateClickResult SUCCESSFUL = new ItemCrateClickResult(Collections.emptyList(), null, true);

    private final List<Condition> notSatisfiedConditions;

    private final ItemStack newItemStack;

    private final boolean successful;

    public ItemCrateClickResult(List<Condition> notSatisfiedConditions, ItemStack newItemStack, boolean successful) {
        this.notSatisfiedConditions = notSatisfiedConditions;
        this.newItemStack = newItemStack;
        this.successful = successful;
    }

    public List<Condition> getNotSatisfiedConditions() {
        return new ArrayList<>(notSatisfiedConditions);
    }

    public Optional<ItemStack> getNewItemStack() {
        return Optional.ofNullable(newItemStack);
    }

    public boolean isSuccessful() {
        return successful;
    }

}

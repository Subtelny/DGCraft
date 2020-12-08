package pl.subtelny.crate.model.item;

import org.bukkit.inventory.ItemStack;

public class ItemCratePrototype {

    private final ItemStack itemStack;

    private final boolean movable;

    private final boolean closeAfterClick;

    public ItemCratePrototype(ItemStack itemStack, boolean movable, boolean closeAfterClick) {
        this.itemStack = itemStack;
        this.movable = movable;
        this.closeAfterClick = closeAfterClick;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isMovable() {
        return movable;
    }

    public boolean isCloseAfterClick() {
        return closeAfterClick;
    }
}

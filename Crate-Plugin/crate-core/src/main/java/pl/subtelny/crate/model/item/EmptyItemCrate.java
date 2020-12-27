package pl.subtelny.crate.model.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EmptyItemCrate implements ItemCrate {

    public static ItemCrate EMPTY = new EmptyItemCrate();

    @Override
    public ItemCrateClickResult click(Player player) {
        return ItemCrateClickResult.SUCCESSFUL;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isCloseAfterClick() {
        return false;
    }

    @Override
    public ItemStack getItemStack() {
        return null;
    }
}

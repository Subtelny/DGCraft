package pl.subtelny.crate.model.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemCrate {

    ItemCrateClickResult click(Player player);

    boolean isMovable();

    ItemStack getItemStack();

}

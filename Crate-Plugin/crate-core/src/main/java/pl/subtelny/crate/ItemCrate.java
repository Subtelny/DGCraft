package pl.subtelny.crate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemCrate {

    ItemCrateClickResult click(Player player);

    ItemStack getItemStack();

}

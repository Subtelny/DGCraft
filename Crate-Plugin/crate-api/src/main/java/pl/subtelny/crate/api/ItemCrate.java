package pl.subtelny.crate.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemCrate {

    ItemCrateClickResult click(Player player, CrateData crateData);

    ItemStack getItemStack();

}

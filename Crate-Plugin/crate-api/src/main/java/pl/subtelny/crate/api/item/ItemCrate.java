package pl.subtelny.crate.api.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.click.ActionType;

public interface ItemCrate {

    ItemCrateClickResult click(Player player, ActionType actionType, CrateData crateData);

    ItemStack getItemStack();

    ItemStack getItemStack(CrateData crateData);

}

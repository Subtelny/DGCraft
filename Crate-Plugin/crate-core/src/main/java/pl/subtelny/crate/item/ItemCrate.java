package pl.subtelny.crate.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.click.ActionType;
import pl.subtelny.crate.CrateData;

import java.util.Map;

public interface ItemCrate {

    ItemCrateClickResult click(Player player, ActionType actionType, CrateData crateData);

    ItemStack getItemStack();

    ItemStack getItemStack(Map<String, String> values);

}

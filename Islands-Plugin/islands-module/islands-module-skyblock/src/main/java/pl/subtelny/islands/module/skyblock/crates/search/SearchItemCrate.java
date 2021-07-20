package pl.subtelny.islands.module.skyblock.crates.search;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.click.ActionType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateClickResult;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.commands.IslandAskCommand;
import pl.subtelny.islands.commands.IslandCommand;
import pl.subtelny.utilities.ColorUtil;

import java.util.Collections;
import java.util.List;

public class SearchItemCrate implements ItemCrate {

    private final ItemStack itemStack;

    private final ItemCrate itemCrate;

    private final IslandId islandId;

    private boolean sent;

    public SearchItemCrate(ItemStack itemStack, ItemCrate itemCrate, IslandId islandId) {
        this.itemStack = itemStack;
        this.itemCrate = itemCrate;
        this.islandId = islandId;
    }

    @Override
    public ItemCrateClickResult click(Player player, ActionType actionType, CrateData crateData) {
        if (sent) {
            return ItemCrateClickResult.SUCCESS;
        }
        ItemCrateClickResult click = itemCrate.click(player, actionType, crateData);
        if (click.isSuccess()) {
            sendAskRequest(player);
            ItemStack item = click.getNewItemStack() == null ? itemStack : click.getNewItemStack();
            ItemStack preparedItemStack = prepareItemStack(item);
            return new ItemCrateClickResult(preparedItemStack, true, false, Collections.emptyList());
        }
        return click;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public ItemStack getItemStack(CrateData crateData) {
        return itemStack;
    }

    private ItemStack prepareItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getLore() != null) {
            List<String> lore = itemMeta.getLore();
            lore.add("");
            lore.add(ColorUtil.color("  &6Wyslano zapytanie o dolaczenie do wyspy."));
            lore.add("");
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private void sendAskRequest(Player player) {
        sent = true;
        String cmd = IslandCommand.class.getAnnotation(PluginCommand.class).command();
        String cmd2 = IslandAskCommand.class.getAnnotation(PluginSubCommand.class).command();
        String cmd3 = islandId.getId() + "";
        Bukkit.dispatchCommand(player, cmd + " " + cmd2 + " " + cmd3);
    }

}

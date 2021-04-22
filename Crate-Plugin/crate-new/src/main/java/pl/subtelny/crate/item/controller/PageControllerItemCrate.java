package pl.subtelny.crate.item.controller;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.click.ActionType;
import pl.subtelny.crate.CrateData;
import pl.subtelny.crate.item.ItemCrate;
import pl.subtelny.crate.item.ItemCrateClickResult;
import pl.subtelny.crate.type.paged.PageController;

import java.util.Map;

public class PageControllerItemCrate implements ItemCrate {

    private final ItemCrate itemCrate;

    private final boolean next;

    public PageControllerItemCrate(ItemCrate itemCrate, boolean next) {
        this.itemCrate = itemCrate;
        this.next = next;
    }

    @Override
    public ItemCrateClickResult click(Player player, ActionType actionType, CrateData crateData) {
        ItemCrateClickResult result = itemCrate.click(player, actionType, crateData);
        if (result.isSuccess()) {
            changePage(player, crateData);
        }
        return result;
    }

    @Override
    public ItemStack getItemStack() {
        return itemCrate.getItemStack();
    }

    @Override
    public ItemStack getItemStack(Map<String, String> values) {
        return itemCrate.getItemStack(values);
    }

    private void changePage(Player player, CrateData crateData) {
        crateData.<PageController>findData("pageController")
                .ifPresent(pageController -> pageController.openPage(player, pageToOpen(crateData)));
    }

    private int pageToOpen(CrateData crateData) {
        int page = crateData.<Integer>findData("pageNumber").orElse(0);
        if (next) {
            return page + 1;
        }
        if (page == 0) {
            return 0;
        }
        return page - 1;
    }

}

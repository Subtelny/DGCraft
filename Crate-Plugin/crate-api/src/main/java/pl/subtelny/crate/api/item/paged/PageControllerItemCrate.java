package pl.subtelny.crate.api.item.paged;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.click.ActionType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateClickResult;

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
    public ItemStack getItemStack(CrateData crateData) {
        return itemCrate.getItemStack(crateData);
    }

    private void changePage(Player player, CrateData crateData) {
        PagedCrateData pagedCrateData = PagedCrateData.of(crateData);
        PagedCrate pagedCrate = pagedCrateData.getPagedCrate();
        int page = pagedCrateData.getPage();

        if (next) {
            pagedCrate.openNextPage(player, page);
        } else {
            pagedCrate.openPreviousPage(player, page);
        }
    }

}

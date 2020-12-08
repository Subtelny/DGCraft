package pl.subtelny.crate.api.prototype;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;

import java.util.Map;

public class PageCratePrototype extends CratePrototype {

    private final ItemStack previousPageItemStack;

    private final ItemStack nextPageItemStack;

    public PageCratePrototype(CrateId crateId,
                              CrateType crateType,
                              String title,
                              int size,
                              Map<Integer, ItemCratePrototype> items,
                              ItemStack previousPageItemStack,
                              ItemStack nextPageItemStack) {
        super(crateId, crateType, title, size, items);
        this.previousPageItemStack = previousPageItemStack;
        this.nextPageItemStack = nextPageItemStack;
    }

    public ItemStack getPreviousPageItemStack() {
        return previousPageItemStack;
    }

    public ItemStack getNextPageItemStack() {
        return nextPageItemStack;
    }
}

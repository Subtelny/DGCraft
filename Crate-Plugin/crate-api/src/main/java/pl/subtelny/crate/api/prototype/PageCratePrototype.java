package pl.subtelny.crate.api.prototype;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;

import java.util.Map;
import java.util.Objects;

public class PageCratePrototype extends CratePrototype {

    private final ItemStack previousPageItemStack;

    private final ItemStack nextPageItemStack;

    public PageCratePrototype(CrateId crateId,
                              CrateType crateType,
                              String title,
                              String permission,
                              int size,
                              Map<Integer, ItemCratePrototype> items,
                              ItemStack previousPageItemStack,
                              ItemStack nextPageItemStack) {
        super(crateId, crateType, title, permission, size, items);
        this.previousPageItemStack = previousPageItemStack;
        this.nextPageItemStack = nextPageItemStack;
    }

    public ItemStack getPreviousPageItemStack() {
        return previousPageItemStack;
    }

    public ItemStack getNextPageItemStack() {
        return nextPageItemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PageCratePrototype that = (PageCratePrototype) o;
        return Objects.equals(previousPageItemStack, that.previousPageItemStack) &&
                Objects.equals(nextPageItemStack, that.nextPageItemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), previousPageItemStack, nextPageItemStack);
    }
}

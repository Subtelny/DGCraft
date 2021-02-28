package pl.subtelny.crate.type.paged;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.ItemCrateClickResult;
import pl.subtelny.utilities.Notification;

import java.util.Objects;

public class PageSwitcherItemCrate implements ItemCrate {

    private final ItemStack itemStack;

    private final Notification notification;

    public PageSwitcherItemCrate(ItemStack itemStack, Notification notification) {
        this.itemStack = itemStack;
        this.notification = notification;
    }

    @Override
    public ItemCrateClickResult click(Player player) {
        notification.sendNotification();
        return ItemCrateClickResult.success(false);
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageSwitcherItemCrate that = (PageSwitcherItemCrate) o;
        return Objects.equals(itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemStack);
    }
}

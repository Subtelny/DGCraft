package pl.subtelny.crate.model.crate;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.inventory.CrateInventory;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.model.item.ItemCrateClickResult;

import java.util.Map;
import java.util.Optional;

public abstract class AbstractCrate implements Crate {

    private final CratePrototype prototype;

    private final CrateInventory inventory;

    protected AbstractCrate(CratePrototype prototype) {
        this.prototype = prototype;
        this.inventory = new CrateInventory(prototype.getSize(), prototype.getTitle(), this);
    }

    @Override
    public boolean click(Player player, int slot) {
        return getItemCrateAtSlot(slot)
                .map(itemCrate -> itemCrate.click(player))
                .map(ItemCrateClickResult::isSuccessful)
                .orElse(false);
    }

    @Override
    public void open(Player player) {
        inventory.addSession(player);
        player.openInventory(inventory);
    }

    @Override
    public void closeAllSessions() {
        inventory.getViewers().forEach(humanEntity -> humanEntity.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }

    protected void renderInventory() {
        ItemStack[] itemStacks = new ItemStack[getInventory().getSize()];
        getItems().forEach((slot, itemCrate) -> itemStacks[slot] = itemCrate.getItemStack());
        inventory.setContents(itemStacks);
    }

    protected void refreshSlot(int slot) {
        Optional<ItemCrate> itemCrateAtSlot = getItemCrateAtSlot(slot);
        itemCrateAtSlot.ifPresentOrElse(
                itemCrate -> inventory.setItem(slot, itemCrate.getItemStack()),
                () -> inventory.clear(slot));
    }

    protected void clearAll() {
        inventory.clear();
    }

    public CratePrototype getPrototype() {
        return prototype;
    }

    public CrateInventory getInventory() {
        return inventory;
    }

    protected abstract Map<Integer, ItemCrate> getItems();

    protected abstract Optional<ItemCrate> getItemCrateAtSlot(int slot);

    @Override
    public CrateId getId() {
        return prototype.getCrateId();
    }
}

package pl.subtelny.crate.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.Validation;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCrate implements Crate {

    private final CrateKey crateKey;

    private final String permission;

    protected final Set<Player> viewers;

    protected final Inventory inventory;

    public AbstractCrate(CrateKey crateKey, String permission, Set<Player> viewers, InventoryInfo inventory) {
        this.crateKey = crateKey;
        this.permission = permission;
        this.viewers = viewers;
        this.inventory = new CrateInventory(inventory, this);
    }

    @Override
    public CrateKey getKey() {
        return crateKey;
    }

    @Override
    public void open(Player player) {
        if (viewers.isEmpty()) {
            render();
        }
        viewers.add(player);
        player.openInventory(inventory);
    }

    @Override
    public CrateClickResult click(Player player, int slot) {
        if (!viewers.contains(player) || (permission != null && !player.hasPermission(permission))) {
            return CrateClickResult.CANNOT_USE;
        }
        return getItemCrateAtSlot(slot)
                .map(itemCrate -> click(player, slot, itemCrate))
                .orElse(CrateClickResult.NOTHING);
    }

    private CrateClickResult click(Player player, int slot, ItemCrate itemCrate) {
        ItemCrateClickResult clickResult = itemCrate.click(player);
        updateItemStack(slot, clickResult);
        return getCrateClickResult(clickResult, slot);
    }

    private CrateClickResult getCrateClickResult(ItemCrateClickResult clickResult, int slot) {
        if (clickResult.isSuccess()) {
            if (clickResult.isMovable()) {
                removeItemCrate(slot);
                return CrateClickResult.CAN_MOVE;
            }
            return CrateClickResult.CANNOT_MOVE;
        }
        return CrateClickResult.ERROR;
    }

    private void updateItemStack(int slot, ItemCrateClickResult click) {
        if (click.isRemoveItem()) {
            updateItemStack(null, slot);
        } else {
            ItemStack newItemStack = click.getNewItemStack();
            if (newItemStack != null) {
                updateItemStack(newItemStack, slot);
            }
        }
    }

    private void updateItemStack(ItemStack itemStack, int slot) {
        Validation.isTrue(slot >= 0, "Slot cannot be less than zero");
        Validation.isTrue(slot < inventory.getSize(), "Slot cannot be higher than inv size");
        inventory.setItem(slot, itemStack);
    }

    public void render() {
        Map<Integer, ItemCrate> itemCrates = getContent();
        itemCrates.forEach((slot, itemCrate) -> inventory.setItem(slot, itemCrate.getItemStack()));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void removeViewer(Player player) {
        viewers.remove(player);
    }

    public boolean hasViewers() {
        return viewers.isEmpty();
    }

    public boolean isViewer(Player player) {
        return viewers.contains(player);
    }

    public abstract Optional<ItemCrate> getItemCrateAtSlot(int slot);

    public abstract void addItemCrate(ItemCrate itemCrate);

    public abstract void removeItemCrate(int slot);

    public abstract Map<Integer, ItemCrate> getContent();
}

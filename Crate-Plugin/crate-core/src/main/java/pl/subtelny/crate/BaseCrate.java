package pl.subtelny.crate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.click.ActionType;
import pl.subtelny.crate.api.click.CrateClickResult;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateClickResult;
import pl.subtelny.crate.api.listener.CrateListener;
import pl.subtelny.crate.inventory.CraftCrateInventory;
import pl.subtelny.crate.inventory.CrateInventory;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BaseCrate implements Crate {

    private final CrateId crateId;

    private final Inventory inventory;

    private final CrateData crateData;

    private final List<Condition> useConditions;

    private final Map<Integer, ItemCrate> itemCrates;

    private final boolean shared;

    private final CrateListener closeCrateListener;

    public BaseCrate(CrateId crateId,
                     CraftCrateInventory inventory,
                     CrateData crateData,
                     CrateListener closeCrateListener,
                     List<Condition> useConditions,
                     Map<Integer, ItemCrate> itemCrates,
                     boolean shared) {
        this.crateId = crateId;
        this.inventory = new CrateInventory(inventory.getName(), inventory.getSize(), this);
        this.crateData = crateData;
        this.useConditions = useConditions;
        this.closeCrateListener = closeCrateListener;
        this.itemCrates = itemCrates;
        this.shared = shared;
    }

    @Override
    public void open(Player player) {
        validateUseConditions(player);
        if (inventory.getViewers().isEmpty()) {
            render();
        }
        player.openInventory(inventory);
    }

    @Override
    public void close(Player player) {
        if (closeCrateListener != null) {
            closeCrateListener.handle(player, this);
        }
    }

    @Override
    public CrateClickResult click(Player player, ActionType actionType, int slot) {
        validateUseConditions(player);

        ItemCrate itemCrate = itemCrates.get(slot);
        if (itemCrate == null) {
            return CrateClickResult.CANT_USE;
        }
        ItemCrateClickResult clickResult = itemCrate.click(player, actionType, crateData);
        return handleClickResult(player, slot, itemCrate, clickResult);
    }

    @Override
    public CrateId getCrateId() {
        return crateId;
    }

    @Override
    public CrateData getCrateData() {
        return crateData;
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public boolean isShared() {
        return shared;
    }

    @Override
    public boolean addItemCrate(ItemCrate itemCrate) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (!itemCrates.containsKey(slot)) {
                itemCrates.put(slot, itemCrate);
                if (!inventory.getViewers().isEmpty()) {
                    render(slot);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void setItemCrate(int slot, ItemCrate itemCrate) {
        Validation.isTrue(slot < inventory.getSize(), "Out of inventory size, " + slot);
        itemCrates.put(slot, itemCrate);

        if (!inventory.getViewers().isEmpty()) {
            render(slot);
        }
    }

    protected CrateClickResult handleClickResult(Player player, int slot, ItemCrate itemCrate, ItemCrateClickResult
            clickResult) {
        if (!clickResult.isSuccess()) {
            informNotSatisfiedConditions(player, clickResult.getNotSatisfiedConditions());
            return CrateClickResult.CANT_USE;
        }
        if (clickResult.isCloseCrate()) {
            return CrateClickResult.CLOSE_INV;
        }
        if (clickResult.isUpdateItemStack()) {
            inventory.setItem(slot, clickResult.getNewItemStack());
        }
        return CrateClickResult.SUCCESS;
    }

    protected ItemStack getItemStack(ItemCrate itemCrate) {
        return itemCrate.getItemStack(crateData);
    }

    protected void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    private void render() {
        inventory.clear();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            render(slot);
        }
    }

    private void render(int slot) {
        ItemCrate itemCrate = itemCrates.get(slot);
        if (itemCrate == null) {
            inventory.clear(slot);
        } else {
            setItem(slot, getItemStack(itemCrate));
        }
    }

    private void informNotSatisfiedConditions(Player player, List<Condition> notSatisfiedConditions) {
        Messages messages = CrateMessages.get();
        notSatisfiedConditions.forEach(condition -> messages.sendTo(player, condition.getMessageKey().getKey(), condition.getMessageKey().getObjects()));
    }

    private void validateUseConditions(Player player) {
        if (!isShared()) {
            if (inventory.getViewers().size() > 1) {
                throw ValidationException.of("Non shared crate cannot be used by more than one player");
            }
        }
        useConditions.stream()
                .filter(condition -> !condition.satisfiesCondition(player))
                .findFirst()
                .map(Condition::getMessageKey)
                .ifPresent(condition -> {
                    throw ValidationException.of(condition.getKey(), condition.getObjects());
                });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseCrate baseCrate = (BaseCrate) o;
        return Objects.equals(crateId, baseCrate.crateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crateId);
    }
}

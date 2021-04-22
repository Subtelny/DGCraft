package pl.subtelny.crate;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.click.ActionType;
import pl.subtelny.crate.click.CrateClickResult;
import pl.subtelny.crate.item.ItemCrate;
import pl.subtelny.crate.item.ItemCrateClickResult;
import pl.subtelny.islands.island.message.IslandMessages;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

import java.util.List;
import java.util.Map;

public class BaseCrate implements Crate {

    private final CrateId crateId;

    private final Inventory inventory;

    private final CrateData crateData;

    private final List<Condition> useConditions;

    private final Map<Integer, ItemCrate> itemCrates;

    private final boolean shared;

    public BaseCrate(CrateId crateId,
                     Inventory inventory,
                     CrateData crateData,
                     List<Condition> useConditions,
                     Map<Integer, ItemCrate> itemCrates,
                     boolean shared) {
        this.crateId = crateId;
        this.inventory = inventory;
        this.crateData = crateData;
        this.useConditions = useConditions;
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
    public CrateClickResult click(Player player, ActionType actionType, int slot) {
        validateUseConditions(player);

        ItemCrate itemCrate = itemCrates.get(slot);
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
    public boolean isShared() {
        return false;
    }

    protected CrateClickResult handleClickResult(Player player, int slot, ItemCrate itemCrate, ItemCrateClickResult clickResult) {
        if (!clickResult.isSuccess()) {
            informNotSatisfiedConditions(player, clickResult.getNotSatisfiedConditions());
            return CrateClickResult.CANT_USE;
        }
        if (clickResult.isCloseCrate()) {
            player.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
            return CrateClickResult.ERROR;
        }
        if (clickResult.isUpdateItemStack()) {
            inventory.setItem(slot, clickResult.getNewItemStack());
        }
        return CrateClickResult.SUCCESS;
    }

    protected ItemStack getItemStack(ItemCrate itemCrate) {
        return itemCrate.getItemStack(crateData.getValues());
    }

    protected void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    private void render() {
        inventory.clear();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemCrate itemCrate = itemCrates.get(slot);
            if (itemCrate != null) {
                setItem(slot, getItemStack(itemCrate));
            }
        }
    }

    private void informNotSatisfiedConditions(Player player, List<Condition> notSatisfiedConditions) {
        Messages messages = IslandMessages.get();
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

}

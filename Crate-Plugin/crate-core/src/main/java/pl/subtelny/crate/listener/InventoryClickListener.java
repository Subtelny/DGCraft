package pl.subtelny.crate.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateClickResult;
import pl.subtelny.crate.inventory.CrateInventory;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class InventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Inventory clickedInv = e.getClickedInventory();
        if (isAnyCrateInv(inv, clickedInv)) {
            e.setCancelled(true);

            Player whoClicked = (Player) e.getWhoClicked();
            if (!hasPermissionToUse(whoClicked.getPlayer(), inv, clickedInv)) {
                whoClicked.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
                return;
            }
            if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                return;
            }
            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                if (isCrateInv(clickedInv)) {
                    CrateInventory clickedInventory = (CrateInventory) clickedInv;
                    click(whoClicked, clickedInventory, e.getSlot());
                }
            }
        }
    }

    private void click(Player player, CrateInventory crateInventory, int slot) {
        try {
            CrateClickResult clickResult = crateInventory.click(player, slot);
            handleClickResult(player, crateInventory, clickResult);
        } catch (ValidationException e) {
            CrateMessages.get().sendTo(player, e.getMessage(), e.getValues());
        }
    }

    private void handleClickResult(Player player, CrateInventory crateInventory, CrateClickResult result) {
        if (result == CrateClickResult.CLOSE_INV) {
            crateInventory.removeSession(player);
            player.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
        }
    }

    private boolean hasPermissionToUse(Player player, Inventory clickedInv, Inventory inv) {
        return hasPermissionToUse(player, clickedInv) || hasPermissionToUse(player, inv);
    }

    private boolean hasPermissionToUse(Player player, Inventory inventory) {
        if (isCrateInv(inventory)) {
            CrateInventory inv = (CrateInventory) inventory;
            return inv.hasSession(player);
        }
        return true;
    }

    private boolean isAnyCrateInv(Inventory clickedInv, Inventory inv) {
        return isCrateInv(clickedInv) || isCrateInv(inv);
    }

    private boolean isCrateInv(Inventory inventory) {
        return inventory instanceof CrateInventory;
    }

}

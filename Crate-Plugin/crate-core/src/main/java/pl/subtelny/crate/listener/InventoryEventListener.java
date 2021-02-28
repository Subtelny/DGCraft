package pl.subtelny.crate.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateClickResult;
import pl.subtelny.crate.inventory.CrateInventory;

@Component
public class InventoryEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory clickedInventory = e.getClickedInventory();
        if (anyIsCrate(e.getInventory(), clickedInventory)) {
            e.setCancelled(true);

            if (canCrateClick(clickedInventory, e.getClick(), e.getAction())) {
                CrateInventory crateInventory = (CrateInventory) clickedInventory;
                Crate crate = crateInventory.getCrate();
                Player whoClicked = (Player) e.getWhoClicked();
                if (crate == null) {
                    whoClicked.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
                } else {
                    boolean cancel = click(crate, whoClicked, e.getSlot());
                    e.setCancelled(cancel);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        Inventory inventory = e.getInventory();
        if (isCrateInventory(inventory)) {
            CrateInventory crateInventory = (CrateInventory) inventory;
            boolean isViewer = crateInventory.isViewer((Player) e.getPlayer());
            if (!isViewer) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        if (inventory instanceof CrateInventory) {
            Player player = (Player) e.getPlayer();
            ((CrateInventory) inventory).close(player);
        }
    }

    private boolean click(Crate crate, Player whoClicked, int slot) {
        CrateClickResult result = crate.click(whoClicked, slot);
        if (CrateClickResult.CANNOT_USE == result) {
            whoClicked.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
        }
        return cancelEvent(result);
    }

    private boolean cancelEvent(CrateClickResult result) {
        switch (result) {
            case CAN_MOVE:
            case NOTHING:
                return false;
            default:
                return true;
        }
    }

    private boolean canCrateClick(Inventory clickedInventory, ClickType clickType, InventoryAction action) {
        return isCrateInventory(clickedInventory) &&
                clickType == ClickType.LEFT &&
                action == InventoryAction.PICKUP_ALL;
    }

    private boolean anyIsCrate(Inventory inventoryFirst, Inventory inventorySecond) {
        return isCrateInventory(inventoryFirst) || isCrateInventory(inventorySecond);
    }

    private boolean isCrateInventory(Inventory inventory) {
        return inventory instanceof CrateInventory;
    }

}

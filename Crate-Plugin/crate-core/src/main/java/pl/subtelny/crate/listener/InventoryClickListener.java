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
import pl.subtelny.crate.inventory.CrateInventory;

@Component
public class InventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Inventory clickedInv = e.getClickedInventory();
        if (isCrateInv(inv) || isCrateInv(clickedInv)) {
            e.setCancelled(true);

            Player whoClicked = (Player) e.getWhoClicked();
            if (!(checkPermissions(inv, whoClicked) || checkPermissions(clickedInv, whoClicked))) {
                whoClicked.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
                return;
            }
            if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                return;
            }
            if (e.getAction() == InventoryAction.PICKUP_ONE) {
                if (isCrateInv(clickedInv)) {
                    CrateInventory clickedInventory = (CrateInventory) clickedInv;
                    clickedInventory.click(whoClicked, e.getSlot());
                }
            }

        }
    }

    private boolean checkPermissions(Inventory inventory, Player player) {
        if (isCrateInv(inventory)) {
            CrateInventory inv = (CrateInventory) inventory;
            return inv.hasSession(player);
        }
        return true;
    }

    private boolean isCrateInv(Inventory inventory) {
        return inventory instanceof CrateInventory;
    }

}

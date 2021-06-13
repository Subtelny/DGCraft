package pl.subtelny.crate.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.inventory.CrateInventory;

@Component
public class InventoryCloseListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        if (isCrateInventory(inventory)) {
            CrateInventory crateInventory = (CrateInventory) inventory;
            crateInventory.getCrate().close((Player) e.getPlayer());
        }
    }

    private boolean isCrateInventory(Inventory inventory) {
        return inventory instanceof CrateInventory;
    }

}

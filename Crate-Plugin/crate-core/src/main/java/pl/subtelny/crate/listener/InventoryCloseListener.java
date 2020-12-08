package pl.subtelny.crate.listener;

import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.inventory.CrateInventory;

@Component
public class InventoryCloseListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory() instanceof CraftInventory) {
            Player player = (Player) e.getPlayer();
            CrateInventory crateInventory = (CrateInventory) e.getInventory();
            crateInventory.removeSession(player);
            crateInventory.cleanIfNeeded();
        }
    }

}

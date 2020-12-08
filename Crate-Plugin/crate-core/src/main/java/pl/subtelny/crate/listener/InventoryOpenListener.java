package pl.subtelny.crate.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.inventory.CrateInventory;

@Component
public class InventoryOpenListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory() instanceof CrateInventory) {
            Player player = (Player) e.getPlayer();
            CrateInventory crateInventory = (CrateInventory) e.getInventory();
            boolean cancelled = !crateInventory.hasSession(player);
            e.setCancelled(cancelled);
        }
    }

}

package pl.subtelny.gui.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.util.CrateUtil;

@Component
public class InventoryOpenListener implements Listener {

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public InventoryOpenListener(PlayerCrateSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventory(InventoryOpenEvent e) {
        HumanEntity entity = e.getPlayer();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        Inventory inventory = e.getInventory();
        if (CrateUtil.isCrateInventory(inventory)) {
            if (!sessionService.hasSession(player)) {
                player.closeInventory();
            }
        }
    }

}

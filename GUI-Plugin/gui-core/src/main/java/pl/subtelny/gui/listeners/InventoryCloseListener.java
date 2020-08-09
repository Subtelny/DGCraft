package pl.subtelny.gui.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.util.CrateUtil;

@Component
public class InventoryCloseListener implements Listener {

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public InventoryCloseListener(PlayerCrateSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventory(InventoryCloseEvent e) {
        HumanEntity entity = e.getPlayer();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (CrateUtil.isCrateInventory(e.getInventory())) {
            sessionService.closeSession(player);
        }
    }

}

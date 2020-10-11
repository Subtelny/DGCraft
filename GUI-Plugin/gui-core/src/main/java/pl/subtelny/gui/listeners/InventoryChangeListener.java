package pl.subtelny.gui.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.events.CrateInventoryChangeEvent;

@Component
public class InventoryChangeListener implements Listener {

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public InventoryChangeListener(PlayerCrateSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @EventHandler()
    public void onInventory(CrateInventoryChangeEvent e) {
        HumanEntity entity = e.getPlayer();
        if (!(entity instanceof Player)) {
            return;
        }
        sessionService.openSession((Player) entity, e.getCrateId());
    }
}

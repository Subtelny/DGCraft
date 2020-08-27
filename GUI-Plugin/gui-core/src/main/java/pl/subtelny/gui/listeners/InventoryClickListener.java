package pl.subtelny.gui.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.crate.inventory.CraftCrateInventory;
import pl.subtelny.gui.api.crate.session.PlayerCrateSession;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.util.CrateUtil;

import java.util.Optional;

@Component
public class InventoryClickListener implements Listener {

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public InventoryClickListener(PlayerCrateSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventory(InventoryClickEvent e) {
        HumanEntity whoClicked = e.getWhoClicked();
        if (!isPlayer(whoClicked)) {
            return;
        }
        Player player = (Player) whoClicked;
        Inventory topInventory = e.getInventory();
        if (CrateUtil.isCrateInventory(topInventory)) {
            cancelEvent(e);

            Optional<PlayerCrateSession> sessionOpt = sessionService.getSession(player);
            if (sessionOpt.isEmpty()) {
                player.closeInventory();
                return;
            }

            PlayerCrateSession session = sessionOpt.get();
            CraftCrateInventory crateInventory = (CraftCrateInventory) topInventory;
            if (!session.isSameInventory(crateInventory)) {
                player.closeInventory();
                return;
            }

            Inventory clickedInventory = e.getClickedInventory();
            if (e.getSlotType() == InventoryType.SlotType.CONTAINER && clickedInventory != null) {
                if (clickedInventory.equals(topInventory)) {
                    session.click(e.getSlot());
                }
            }
        }
    }

    private void cancelEvent(InventoryClickEvent e) {
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
    }

    private boolean isPlayer(HumanEntity entity) {
        return entity instanceof Player;
    }

}

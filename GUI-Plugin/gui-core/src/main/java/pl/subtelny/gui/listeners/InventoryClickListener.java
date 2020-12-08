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
import pl.subtelny.gui.api.crate.session.PlayerCrateSession;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.crate.inventory.CraftCrateInventory;
import pl.subtelny.gui.messages.CrateMessages;
import pl.subtelny.gui.util.CrateUtil;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@Component
public class InventoryClickListener implements Listener {

    private final PlayerCrateSessionService sessionService;

    private final CrateMessages crateMessages;

    @Autowired
    public InventoryClickListener(PlayerCrateSessionService sessionService, CrateMessages crateMessages) {
        this.sessionService = sessionService;
        this.crateMessages = crateMessages;
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
                player.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
                return;
            }

            PlayerCrateSession session = sessionOpt.get();
            CraftCrateInventory crateInventory = (CraftCrateInventory) topInventory;
            if (!session.isSameInventory(crateInventory)) {
                player.closeInventory(InventoryCloseEvent.Reason.CANT_USE);
                return;
            }

            Inventory clickedInventory = e.getClickedInventory();
            if (e.getSlotType() == InventoryType.SlotType.CONTAINER && clickedInventory != null) {
                if (clickedInventory.equals(topInventory)) {
                    click(player, session, e.getSlot());
                }
            }
        }
    }

    private void click(Player player, PlayerCrateSession session, int slot) {
        try {
            session.click(slot);
        } catch (ValidationException e) {
            crateMessages.sendTo(player, e.getMessage(), e.getValues());
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

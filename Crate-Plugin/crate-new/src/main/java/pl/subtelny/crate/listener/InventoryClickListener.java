package pl.subtelny.crate.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateInventory;
import pl.subtelny.crate.click.ActionType;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class InventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory topInv = e.getInventory();
        if (isCrateInventory(topInv)) {
            cancelEvent(e);
            if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                return;
            }

            Inventory clickedInv = e.getClickedInventory();
            if (topInv.equals(clickedInv)) {
                Crate crate = getCrate(topInv);
                click(e, crate);
            }
        }
    }

    private void click(InventoryClickEvent e, Crate crate) {
        try {
            click((Player) e.getWhoClicked(), crate, e.getClick(), e.getSlot());
        } catch (ValidationException ex) {
            CrateMessages.get().sendTo(e.getWhoClicked(), ex.getMessage(), ex.getValues());
            e.getWhoClicked().closeInventory(InventoryCloseEvent.Reason.CANT_USE);
        }
    }

    private void click(Player player, Crate crate, ClickType clickType, int slot) {
        ActionType actionType = toActionType(clickType);
        // zrobic obsluge innych ActionType
        if (actionType.isClick()) {
            crate.click(player, actionType, slot);
        }
    }

    private ActionType toActionType(ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            return ActionType.LEFT_CLICK;
        }
        return ActionType.OTHER;
    }

    private void cancelEvent(InventoryClickEvent e) {
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
    }

    private Crate getCrate(Inventory inventory) {
        return ((CrateInventory) inventory).getCrate();
    }

    private boolean isCrateInventory(Inventory inventory) {
        return inventory instanceof CrateInventory;
    }

}

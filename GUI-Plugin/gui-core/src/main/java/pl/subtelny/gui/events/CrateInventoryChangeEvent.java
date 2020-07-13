package pl.subtelny.gui.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.InventoryView;
import pl.subtelny.gui.api.crate.model.CrateId;

public class CrateInventoryChangeEvent extends InventoryEvent {

    private static final HandlerList handlers = new HandlerList();

    private final CrateId crateId;

    public CrateInventoryChangeEvent(InventoryView transaction, CrateId crateId) {
        super(transaction);
        this.crateId = crateId;
    }

    public CrateId getCrateId() {
        return crateId;
    }

    public final HumanEntity getPlayer() {
        return transaction.getPlayer();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

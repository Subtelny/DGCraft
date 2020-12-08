package pl.subtelny.crate.inventory;

import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrateInventory extends CraftInventoryCustom {

    private final List<Player> sessions = new ArrayList<>();

    private final Set<Integer> movableSlots = new HashSet<>();

    public CrateInventory(int size, String title) {
        super(null, size, title);
    }

    @Override
    public void clear() {
        super.clear();
        getViewers().forEach(humanEntity -> humanEntity.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
        sessions.clear();
        movableSlots.clear();
    }

    public void addSession(Player player) {
        sessions.add(player);
    }

    public void removeSession(Player player) {
        sessions.remove(player);
    }

    public boolean hasSession(Player player) {
        return sessions.contains(player);
    }

    public boolean isMovable(int slot) {
        return movableSlots.contains(slot);
    }

    public void setMovableSlot(int slot, boolean movable) {
        if (movable) {
            movableSlots.add(slot);
        } else {
            movableSlots.remove(slot);
        }
    }

}

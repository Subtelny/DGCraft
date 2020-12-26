package pl.subtelny.crate.inventory;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateClickResult;
import pl.subtelny.crate.api.CrateId;

import java.util.ArrayList;
import java.util.List;

public class CrateInventory extends CraftInventoryCustom {

    private final Crate crate;

    private final List<Player> sessions = new ArrayList<>();

    public CrateInventory(int size, String title, Crate crate) {
        super(null, size, title);
        this.crate = crate;
    }

    @Override
    public void clear() {
        super.clear();
        getViewers().forEach(humanEntity -> humanEntity.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
        sessions.clear();
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

    public void cleanIfNeeded() {
        if (getViewers().isEmpty()) {
            sessions.clear();
        }
        crate.cleanIfNeeded();
    }

    public CrateClickResult click(Player player, int slot) {
        if (hasSession(player)) {
            return crate.click(player, slot);
        }
        return CrateClickResult.CLOSE_INV;
    }

    public CrateId getCrateId() {
        return crate.getId();
    }

}

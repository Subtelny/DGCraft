package pl.subtelny.gui.crate.model;

import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitTask;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.ItemCrate;
import pl.subtelny.gui.api.crate.session.PlayerCrateSession;
import pl.subtelny.gui.crate.CrateConditionsService;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrateImpl extends CraftInventoryCustom implements Crate {

    private final CratePrototype prototype;

    private final CrateConditionsService conditionsService;

    private final Map<Integer, ItemCrate> items = new HashMap<>();

    private final Map<Integer, BukkitTask> tasks = new HashMap<>();

    private final Map<Player, PlayerCrateSession> sessions = new HashMap<>();

    public CrateImpl(InventoryHolder owner, CratePrototype prototype, CrateConditionsService conditionsService) {
        super(owner, InventoryType.CHEST);
        this.prototype = prototype;
        this.conditionsService = conditionsService;
    }

    @Override
    public void click(Player player, int slot) {
        validateSession(player);
        Optional.ofNullable(getItemCrate(slot))
                .ifPresent(itemCrate -> click(player, itemCrate));
    }

    private void click(Player player, ItemCrate itemCrate) {

    }

    @Override
    public ItemCrate getItemCrate(int slot) {
        return items.get(slot);
    }

    @Override
    public void setItemCrate(int slot, ItemCrate itemCrate) {
        int size = getSize();
        if (slot < 0 || slot >= size) {
            throw new IllegalStateException("Cannot set itemCrate at slot " + slot + " when size of inv is " + size);
        }
        items.put(slot, itemCrate);
        setItem(slot, itemCrate.get);
    }

    private void validateSession(Player player) {
        if (!sessions.containsKey(player)) {
            throw ValidationException.of("crate.no_session");
        }
    }

}

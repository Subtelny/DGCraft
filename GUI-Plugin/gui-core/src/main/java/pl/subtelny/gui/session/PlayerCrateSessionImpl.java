package pl.subtelny.gui.session;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.model.ItemCrate;
import pl.subtelny.gui.api.crate.session.PlayerCrateSession;
import pl.subtelny.gui.crate.CrateConditionsService;
import pl.subtelny.gui.events.CrateInventoryChangeEvent;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.condition.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayerCrateSessionImpl implements PlayerCrateSession {

    private final Player player;

    private final Crate crate;

    private final CrateInventory inventory;

    private final CrateConditionsService conditionsService;

    private Map<Integer, BukkitTask> tasks = new HashMap<>();

    public PlayerCrateSessionImpl(Player player, Crate crate, CrateInventory inventory, CrateConditionsService conditionsService) {
        this.player = player;
        this.crate = crate;
        this.inventory = inventory;
        this.conditionsService = conditionsService;
    }

    @Override
    public boolean isSameInventory(CrateInventory crateInventory) {
        return crateInventory.getCrateId().equals(crate.getId());
    }

    @Override
    public void openCrateInventory() {
        player.openInventory(inventory);
    }

    @Override
    public void closeCrateInventory() {
        player.closeInventory();
        stopAllTasks();
    }

    @Override
    public Crate getCrate() {
        return crate;
    }

    @Override
    public void click(int slot) {
        if (!tasks.containsKey(slot)) {
            Optional<ItemCrate> itemCrateOpt = crate.getItemAt(slot);
            itemCrateOpt.ifPresent(itemCrate -> click(slot, itemCrate));
        }
    }

    private void click(int slot, ItemCrate itemCrate) {
        List<Condition> notStatisfiedConditions = itemCrate.getNotStatisfiedConditions(player);
        if (notStatisfiedConditions.isEmpty()) {
            itemCrate.satisfyCostConditions(player);
            itemCrate.rewardPlayer(player);
            itemCrate.getCrateToOpen().ifPresent(this::changeCrateInventory);
        } else {
            if (crate.isGlobal()) {
                conditionsService.informAboutNotSatisfiedConditions(player, notStatisfiedConditions);
            } else {
                setNotSatisfiedConditionItem(slot, notStatisfiedConditions);
            }
        }
    }

    @Override
    public void refreshSlot(int slot) {
        if (tasks.containsKey(slot)) {
            tasks.get(slot).cancel();
            tasks.remove(slot);
        }
        Optional<ItemCrate> itemAt = crate.getItemAt(slot);
        itemAt.ifPresentOrElse(itemCrate -> inventory.setItem(slot, itemCrate.getOriginalItemStack()),
                () -> inventory.clear(slot));
    }

    private void setNotSatisfiedConditionItem(int slot, List<Condition> notStatisfiedConditions) {
        conditionsService.computeNotSatisfiedItemStack(notStatisfiedConditions).ifPresent(itemStack -> {
            inventory.setItem(slot, itemStack);
            BukkitTask task = JobsProvider.runSyncLater(GUI.plugin, () -> refreshSlot(slot), 20 * 5L);
            tasks.put(slot, task);
        });
    }

    private void changeCrateInventory(CrateId crateId) {
        closeCrateInventory();
        Bukkit.getPluginManager().callEvent(new CrateInventoryChangeEvent(player.getOpenInventory(), crateId));
    }

    private void stopAllTasks() {
        tasks.values().forEach(BukkitTask::cancel);
        tasks.clear();
    }

}

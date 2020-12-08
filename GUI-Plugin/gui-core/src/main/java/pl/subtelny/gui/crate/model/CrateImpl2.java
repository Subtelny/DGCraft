package pl.subtelny.gui.crate.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.model.ItemCrate;
import pl.subtelny.gui.crate.CrateConditionsService;
import pl.subtelny.gui.events.CrateInventoryChangeEvent;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.job.JobsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrateImpl2 implements Crate {

    private final CratePrototype cratePrototype;

    private final CrateConditionsService conditionsService;

    private final Map<Integer, ItemCrate> items = new HashMap<>();

    private final Map<Integer, BukkitTask> tasks = new HashMap<>();

    public CrateImpl2(CratePrototype cratePrototype, CrateConditionsService conditionsService) {
        this.cratePrototype = cratePrototype;
        this.conditionsService = conditionsService;
    }

    @Override
    public void click(Player player, int slot) {
        Optional.ofNullable(items.get(slot))
                .ifPresent(itemCrate -> click(player, slot, itemCrate));
    }

    @Override
    public ItemCrate getItem(int slot) {
        return null;
    }

    @Override
    public void setItem(int slot, ItemCrate itemCrate) {

    }

    private void click(Player player, int slot, ItemCrate itemCrate) {
        List<Condition> notStatisfiedConditions = itemCrate.getNotStatisfiedConditions(player);
        if (notStatisfiedConditions.isEmpty()) {
            itemCrate.satisfyCostConditions(player);
            itemCrate.rewardPlayer(player);
            if (itemCrate.isCloseAfterClick()) {
                closeCrateInventory();
            }
        } else {
            if (crate.isGlobal()) {
                conditionsService.getNotSatisfiedConditions(player, notStatisfiedConditions);
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

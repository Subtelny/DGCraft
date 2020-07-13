package pl.subtelny.gui.session;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.model.ItemCrate;
import pl.subtelny.gui.crate.CrateService;
import pl.subtelny.gui.crate.inventory.CraftCrateInventory;
import pl.subtelny.gui.events.CrateInventoryChangeEvent;
import pl.subtelny.gui.messages.CrateMessages;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayerCrateSession {

    private final Player player;

    private final Crate crate;

    private final CrateInventory inventory;

    private final CrateService crateService;

    private final CrateMessages crateMessages;

    private Map<Integer, BukkitTask> tasks = new HashMap<>();

    public PlayerCrateSession(Player player, Crate crate, CrateInventory inventory, CrateService crateService, CrateMessages crateMessages) {
        this.player = player;
        this.crate = crate;
        this.inventory = inventory;
        this.crateService = crateService;
        this.crateMessages = crateMessages;
    }

    public boolean isSameInventory(CraftCrateInventory crateInventory) {
        return crateInventory.getCrateId().equals(crate.getId());
    }

    public void openCrateInventory() {
        player.openInventory(inventory);
    }

    public void closeCrateInventory() {
        player.closeInventory();
        stopAllTasks();
    }

    public Crate getCrate() {
        return crate;
    }

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
                sendNotSatisfiedConditionMessages(notStatisfiedConditions);
            } else {
                setNotSatisfiedConditionItem(slot, notStatisfiedConditions);
            }
        }
    }

    public void refreshSlot(int slot) {
        if (tasks.containsKey(slot)) {
            tasks.get(slot).cancel();
            tasks.remove(slot);
        }
        Optional<ItemCrate> itemAt = crate.getItemAt(slot);
        itemAt.ifPresentOrElse(itemCrate -> inventory.setItem(slot, itemCrate.getOriginalItemStack()),
                () -> inventory.setItem(slot, null));
    }

    private void sendNotSatisfiedConditionMessages(List<Condition> notSatisfiedConditions) {
        notSatisfiedConditions.forEach(condition -> {
            MessageKey messageKey = condition.getMessageKey();
            crateMessages.sendTo(player, messageKey.getKey(), messageKey.getObjects());
        });
    }

    private void setNotSatisfiedConditionItem(int slot, List<Condition> notStatisfiedConditions) {
        crateService.getNotSatisfiedItemStack(notStatisfiedConditions).ifPresent(itemStack -> {
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

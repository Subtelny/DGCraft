package pl.subtelny.crate.model.crate.personal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.api.CrateClickResult;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.model.crate.AbstractCrate;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.model.item.ItemCrateClickResult;

import java.util.*;

public class PersonalCrate extends AbstractCrate {

    public static final CrateType PERSONAL_TYPE = new CrateType("PERSONAL");

    private final Map<Integer, ItemCrate> items;

    private final List<BukkitTask> tasks = new ArrayList<>();

    protected PersonalCrate(CratePrototype prototype, Map<Integer, ItemCrate> items) {
        super(prototype);
        this.items = items;
    }

    @Override
    protected CrateClickResult click(Player player, ItemCrate itemCrate) {
        ItemCrateClickResult result = itemCrate.click(player);
        handleNotSatisfiedClick(itemCrate, result);
        return getCrateClickResult(itemCrate, result);
    }

    private void handleNotSatisfiedClick(ItemCrate itemCrate, ItemCrateClickResult result) {
        Optional<Integer> slotOpt = getItems().entrySet().stream()
                .filter(entry -> entry.getValue().equals(itemCrate))
                .map(Map.Entry::getKey)
                .findFirst();

        Optional<ItemStack> itemStackToReplaceOpt = result.getNewItemStack();
        if (slotOpt.isPresent() && itemStackToReplaceOpt.isPresent()) {
            handleNotSatisfiedClick(slotOpt.get(), itemStackToReplaceOpt.get());
        }
    }

    private void handleNotSatisfiedClick(int slot, ItemStack itemStackToReplace) {
        getInventory().setItem(slot, itemStackToReplace);
        BukkitTask task = Bukkit.getScheduler().runTaskLater(Crate.plugin, () -> refreshSlot(slot), 20L * 5);
        tasks.add(task);
    }

    @Override
    public void cleanIfNeeded() {
        if (getInventory().getViewers().isEmpty()) {
            clearAll();
        }
    }

    @Override
    protected Map<Integer, ItemCrate> getItems() {
        return new HashMap<>(items);
    }

    @Override
    protected Optional<ItemCrate> getItemCrateAtSlot(int slot) {
        return Optional.ofNullable(items.get(slot));
    }

    @Override
    protected void clearAll() {
        super.clearAll();
        tasks.forEach(BukkitTask::cancel);
        tasks.clear();
        items.clear();
    }

}

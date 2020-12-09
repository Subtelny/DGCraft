package pl.subtelny.crate.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.model.item.ItemCrateClickResult;

import java.util.*;

public class PersonalCrate extends AbstractCrate {

    private final Map<Integer, ItemCrate> items;

    private final List<BukkitTask> tasks = new ArrayList<>();

    protected PersonalCrate(CratePrototype prototype, Map<Integer, ItemCrate> items) {
        super(prototype);
        this.items = items;
    }

    @Override
    public boolean click(Player player, int slot) {
        Optional<ItemCrateClickResult> result = getItemCrateAtSlot(slot)
                .map(itemCrate -> itemCrate.click(player));
        result.flatMap(ItemCrateClickResult::getNewItemStack)
                .ifPresent(itemStack -> handleNotSatisfiedClick(itemStack, slot));
        return result
                .map(ItemCrateClickResult::isSuccessful)
                .orElse(false);
    }

    private void handleNotSatisfiedClick(ItemStack itemStack, int slot) {
        getInventory().setItem(slot, itemStack);
        Bukkit.getScheduler().runTaskLater(Crate.plugin, () -> refreshSlot(slot), 20L * 5);
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

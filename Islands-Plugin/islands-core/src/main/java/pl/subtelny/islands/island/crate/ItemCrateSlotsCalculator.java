package pl.subtelny.islands.island.crate;

import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ItemCrateSlotsCalculator {

    private final PageCratePrototype cratePrototype;

    protected ItemCrateSlotsCalculator(PageCratePrototype cratePrototype) {
        this.cratePrototype = cratePrototype;
    }

    protected Map<Integer, ItemCratePrototype> getItemCrates(List<ItemCratePrototype> islandItems, int pages) {
        Map<Integer, ItemCratePrototype> itemCrates = new HashMap<>();

        Iterator<ItemCratePrototype> itemsToAdd = islandItems.iterator();
        for (int page = 1; page <= pages; page++) {
            Iterator<Integer> emptySlots = getEmptySlots(page).iterator();
            while (itemsToAdd.hasNext() && emptySlots.hasNext()) {
                ItemCratePrototype itemCrate = itemsToAdd.next();
                Integer slot = emptySlots.next();
                itemCrates.put(slot, itemCrate);
            }
        }

        Map<Integer, ItemCratePrototype> predefinedItems = cratePrototype.getItems();
        itemCrates.putAll(predefinedItems);
        return itemCrates;
    }

    protected int countNeededPagesForSlots(int slots) {
        int toOccupy = slots;
        int page = 1;

        while (toOccupy > 0) {
            int emptySlotsAtPage = getEmptySlots(page).size();
            toOccupy -= emptySlotsAtPage;
            page++;
        }
        return page;
    }

    private List<Integer> getEmptySlots(int page) {
        int slotTo = cratePrototype.getSize() * page;
        int slotFrom = slotTo - cratePrototype.getSize();
        return IntStream.rangeClosed(slotFrom, slotTo)
                .boxed()
                .filter(slot -> !isSlotTaken(page, slot))
                .collect(Collectors.toList());
    }

    private boolean isSlotTaken(int page, int slot) {
        int slotTo = cratePrototype.getSize() * page;
        if (cratePrototype.getItems().containsKey(slot)) {
            return true;
        }
        if (page == 1) {
            return cratePrototype.getStaticItems().containsKey(slotTo);
        }
        return cratePrototype.getStaticItems().containsKey(slot - slotTo);
    }

}

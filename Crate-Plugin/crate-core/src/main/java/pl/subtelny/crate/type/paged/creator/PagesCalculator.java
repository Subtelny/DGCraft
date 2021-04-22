package pl.subtelny.crate.type.paged.creator;

import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.type.paged.PagedCratePrototype;

import java.util.Set;

public class PagesCalculator {

    private final PagedCratePrototype prototype;

    public PagesCalculator(PagedCratePrototype prototype) {
        this.prototype = prototype;
    }

    public int calculateTotalPages() {
        double invSize = prototype.getInventorySize();
        double lastItemSlot = getLastItemSlot();

        Set<ItemCrate> itemCratesToAdd = prototype.getItemCratesToAdd();
        int pages = (int) Math.ceil(lastItemSlot / invSize);
        if (itemCratesToAdd.isEmpty()) {
            return pages;
        }

        int itemsToAdd = itemCratesToAdd.size();
        int pageScanned = 0;
        while (itemsToAdd > 0) {
            itemsToAdd -= getTotalEmptySlotsAtPage(pageScanned, itemsToAdd);
            pageScanned++;
        }
        return Math.max(pageScanned, pages);
    }

    private double getTotalEmptySlotsAtPage(int page, int itemsToAdd) {
        int totalStaticItems = prototype.getStaticContent().size();
        int from = page == 0 ? 0 : prototype.getInventorySize() * page;
        int to = from + prototype.getInventorySize();
        long totalItems = prototype.getContent().keySet().stream().filter(slot -> slot >= from && slot < to).count();
        long total = totalStaticItems + totalItems;
        if (page > 0) {
            total++;
        }
        if (itemsToAdd - total > 0) {
            total++;
        }
        return total;
    }

    private double getLastItemSlot() {
        double lastStaticSlot = prototype.getStaticContent().keySet().stream().max(Integer::compare).orElse(0);
        double lastSlot = prototype.getContent().keySet().stream().max(Integer::compare).orElse(0);
        return Math.max(lastSlot, lastStaticSlot);
    }

}

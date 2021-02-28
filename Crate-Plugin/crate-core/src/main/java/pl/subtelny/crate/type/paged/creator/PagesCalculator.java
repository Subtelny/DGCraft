package pl.subtelny.crate.type.paged.creator;

import pl.subtelny.crate.ItemCrate;

import java.util.Set;

public class PagesCalculator {

    private final PagedCrateCreatorRequest request;

    public PagesCalculator(PagedCrateCreatorRequest request) {
        this.request = request;
    }

    public int calculateTotalPages() {
        double invSize = request.getInventorySize();
        double lastItemSlot = getLastItemSlot();

        Set<ItemCrate> itemCratesToAdd = request.getItemCratesToAdd();
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
        int totalStaticItems = request.getStaticItemCrates().size();
        int from = page == 0 ? 0 : request.getInventorySize() * page;
        int to = from + request.getInventorySize();
        long totalItems = request.getContent().keySet().stream().filter(slot -> slot >= from && slot < to).count();
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
        double lastStaticSlot = request.getStaticItemCrates().keySet().stream().max(Integer::compare).orElse(0);
        double lastSlot = request.getContent().keySet().stream().max(Integer::compare).orElse(0);
        return Math.max(lastSlot, lastStaticSlot);
    }

}

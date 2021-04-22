package pl.subtelny.crate.type.paged.creator;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import pl.subtelny.crate.api.AbstractCrate;
import pl.subtelny.crate.api.ContentCrate;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.type.paged.PagedCratePrototype;
import pl.subtelny.crate.type.paged.PageSwitcherItemCrate;
import pl.subtelny.crate.type.paged.PagedCrate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PagesInitializer {

    private final PagedCrate pagedCrate;

    private final int totalPages;

    private final PagedCratePrototype prototype;

    public PagesInitializer(PagedCrate pagedCrate, int totalPages, PagedCratePrototype prototype) {
        this.pagedCrate = pagedCrate;
        this.totalPages = totalPages;
        this.prototype = prototype;
    }

    public AbstractCrate[] initializePages() {
        AbstractCrate[] pages = new AbstractCrate[totalPages];
        for (int page = 0; page < totalPages; page++) {
            pages[page] = initializePage(page);
        }
        fillUpPagesWithNonSettledItems(pages);
        return pages;
    }

    private void fillUpPagesWithNonSettledItems(AbstractCrate[] pages) {
        Set<ItemCrate> itemCratesToAdd = prototype.getItemCratesToAdd();
        for (AbstractCrate page : pages) {
            if (itemCratesToAdd.isEmpty()) {
                return;
            }
            int emptySlots = countEmptySlots(page);
            Iterable<ItemCrate> itemsToAdd = Iterables.limit(itemCratesToAdd, emptySlots);
            itemCratesToAdd.removeAll(Sets.newHashSet(itemsToAdd));
            itemsToAdd.forEach(page::addItemCrate);
        }
    }

    private int countEmptySlots(AbstractCrate crate) {
        return prototype.getInventorySize() - crate.getContent().size();
    }

    private AbstractCrate initializePage(int page) {
        Map<Integer, ItemCrate> content = getItemCrates(page);
        InventoryInfo inventory = createInventory(prototype);
        return new ContentCrate(prototype.getCrateKey(), prototype.getPermission(), inventory, content);
    }

    private Map<Integer, ItemCrate> getItemCrates(int page) {
        int from = page == 0 ? 0 : prototype.getInventorySize();
        int to = from + prototype.getInventorySize();

        HashMap<Integer, ItemCrate> itemCrates = new HashMap<>(prototype.getStaticContent());
        itemCrates.putAll(getItemCrates(from, to));

        if (page > 0) {
            itemCrates.put(from, getPageSwitcherPreviousItemCrate());
        }
        if (page < totalPages) {
            itemCrates.put(from + 8, getPageSwitcherNextItemCrate());
        }
        return itemCrates;
    }

    private Map<Integer, ItemCrate> getItemCrates(int from, int to) {
        Map<Integer, ItemCrate> itemCrates = prototype.getContent();
        return itemCrates.entrySet().stream()
                .filter(entry -> entry.getKey() >= from && entry.getKey() < to)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private PageSwitcherItemCrate getPageSwitcherNextItemCrate() {
        return new PageSwitcherItemCrate(prototype.getNextPageItemStack(), pagedCrate::nextPage);
    }

    private PageSwitcherItemCrate getPageSwitcherPreviousItemCrate() {
        return new PageSwitcherItemCrate(prototype.getPreviousPageItemStack(), pagedCrate::previousPage);
    }

    private InventoryInfo createInventory(PagedCratePrototype prototype) {
        return InventoryInfo.of(prototype.getTitle(), prototype.getInventorySize());
    }

}

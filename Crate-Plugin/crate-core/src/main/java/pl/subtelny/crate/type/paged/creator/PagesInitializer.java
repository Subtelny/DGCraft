package pl.subtelny.crate.type.paged.creator;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import pl.subtelny.crate.api.AbstractCrate;
import pl.subtelny.crate.api.ContentCrate;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.type.paged.creator.PagedCrateCreatorRequest;
import pl.subtelny.crate.type.paged.PageSwitcherItemCrate;
import pl.subtelny.crate.type.paged.PagedCrate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PagesInitializer {

    private final PagedCrate pagedCrate;

    private final int totalPages;

    private final PagedCrateCreatorRequest request;

    public PagesInitializer(PagedCrate pagedCrate, int totalPages, PagedCrateCreatorRequest request) {
        this.pagedCrate = pagedCrate;
        this.totalPages = totalPages;
        this.request = request;
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
        Set<ItemCrate> itemCratesToAdd = request.getItemCratesToAdd();
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
        return request.getInventorySize() - crate.getContent().size();
    }

    private AbstractCrate initializePage(int page) {
        Map<Integer, ItemCrate> content = getItemCrates(page);
        InventoryInfo inventory = createInventory(request);
        return new ContentCrate(request.getCrateKey(), request.getPermission(), inventory, content);
    }

    private Map<Integer, ItemCrate> getItemCrates(int page) {
        int from = page == 0 ? 0 : request.getInventorySize();
        int to = from + request.getInventorySize();

        HashMap<Integer, ItemCrate> itemCrates = new HashMap<>(request.getStaticItemCrates());
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
        Map<Integer, ItemCrate> itemCrates = request.getContent();
        return itemCrates.entrySet().stream()
                .filter(entry -> entry.getKey() >= from && entry.getKey() < to)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private PageSwitcherItemCrate getPageSwitcherNextItemCrate() {
        return new PageSwitcherItemCrate(request.getNextItemStack(), pagedCrate::nextPage);
    }

    private PageSwitcherItemCrate getPageSwitcherPreviousItemCrate() {
        return new PageSwitcherItemCrate(request.getPreviousItemStack(), pagedCrate::previousPage);
    }

    private InventoryInfo createInventory(PagedCrateCreatorRequest request) {
        return InventoryInfo.of(request.getTitle(), request.getInventorySize());
    }

}

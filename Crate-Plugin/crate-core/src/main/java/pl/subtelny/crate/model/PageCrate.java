package pl.subtelny.crate.model;

import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PageCrate extends PersonalCrate {

    private int currentPage;

    private final List<Page> pages;

    protected PageCrate(CratePrototype prototype, List<Page> pages) {
        super(prototype, new HashMap<>());
        this.pages = pages;
    }

    @Override
    protected Map<Integer, ItemCrate> getItems() {
        return pages.get(currentPage).getItemCrates();
    }

    @Override
    protected Optional<ItemCrate> getItemCrateAtSlot(int slot) {
        return pages.get(currentPage).getItemCrateAtSlot(slot);
    }

    public void nextPage() {
        setPage(currentPage + 1);
    }

    public void previousPage() {
        setPage(currentPage - 1);
    }

    private void setPage(int pageSlot) {
        if (pages.size() > pageSlot && pageSlot >= 0) {
            this.currentPage = pageSlot;
            renderInventory();
        } else {
            throw ValidationException.of("pageCrate.page_not_found", pageSlot);
        }
    }

    @Override
    protected void clearAll() {
        super.clearAll();
        pages.clear();
    }
}

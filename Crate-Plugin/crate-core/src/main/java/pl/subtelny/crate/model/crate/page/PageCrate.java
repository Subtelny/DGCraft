package pl.subtelny.crate.model.crate.page;

import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.model.crate.personal.PersonalCrate;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.*;

public class PageCrate extends PersonalCrate {

    private int currentPage;

    private final List<Page> pages = new ArrayList<>();

    public PageCrate(CratePrototype prototype) {
        super(prototype, new HashMap<>());
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

    public void addPage(Page page) {
        pages.add(page);
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

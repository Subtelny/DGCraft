package pl.subtelny.crate.type.paged;

import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateInventory;

public class PagedCrateInventory extends CrateInventory {

    private final PagedCrate pagedCrate;

    private final int page;

    public PagedCrateInventory(int size, String title, Crate crate, PagedCrate pagedCrate, int page) {
        super(size, title, crate);
        this.pagedCrate = pagedCrate;
        this.page = page;
    }

    public PagedCrate getPagedCrate() {
        return pagedCrate;
    }

    public int getPage() {
        return page;
    }
}

package pl.subtelny.crate.type.paged.creator;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.AbstractCrate;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.api.type.paged.PagedCratePrototype;
import pl.subtelny.crate.type.paged.PagedCrate;

@Component
public class PagedCrateCreatorStrategy implements CrateCreatorStrategy<PagedCratePrototype> {

    @Override
    public Crate create(PagedCratePrototype request) {
        return constructPagedCrate(request);
    }

    @Override
    public CrateType getType() {
        return PagedCratePrototype.TYPE;
    }

    private PagedCrate constructPagedCrate(PagedCratePrototype prototype) {
        PagedCrate pagedCrate = new PagedCrate(prototype.getCrateKey(), prototype.getPermission(), createInventory(prototype));
        AbstractCrate[] pages = getPages(pagedCrate, prototype);
        pagedCrate.setPages(pages);
        return pagedCrate;
    }

    private InventoryInfo createInventory(PagedCratePrototype prototype) {
        return InventoryInfo.of(prototype.getTitle(), prototype.getInventorySize());
    }

    private AbstractCrate[] getPages(PagedCrate pagedCrate, PagedCratePrototype prototype) {
        int totalPages = getTotalPages(prototype);
        return new PagesInitializer(pagedCrate, totalPages, prototype).initializePages();
    }

    private int getTotalPages(PagedCratePrototype prototype) {
        return new PagesCalculator(prototype).calculateTotalPages();
    }

}

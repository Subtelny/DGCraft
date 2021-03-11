package pl.subtelny.crate.type.paged.creator;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.AbstractCrate;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.api.type.paged.PagedCratePrototype;
import pl.subtelny.crate.api.type.paged.creator.PagedCrateCreatorRequest;
import pl.subtelny.crate.type.paged.PagedCrate;

@Component
public class PagedCrateCreatorStrategy implements CrateCreatorStrategy<PagedCrateCreatorRequest> {

    @Override
    public Crate create(PagedCrateCreatorRequest request) {
        return constructPagedCrate(request);
    }

    @Override
    public CrateType getType() {
        return PagedCratePrototype.TYPE;
    }

    private PagedCrate constructPagedCrate(PagedCrateCreatorRequest request) {
        PagedCrate pagedCrate = new PagedCrate(request.getCrateKey(), request.getPermission(), createInventory(request));
        AbstractCrate[] pages = getPages(pagedCrate, request);
        pagedCrate.setPages(pages);
        return pagedCrate;
    }

    private InventoryInfo createInventory(PagedCrateCreatorRequest request) {
        return InventoryInfo.of(request.getTitle(), request.getInventorySize());
    }

    private AbstractCrate[] getPages(PagedCrate pagedCrate, PagedCrateCreatorRequest request) {
        int totalPages = getTotalPages(request);
        return new PagesInitializer(pagedCrate, totalPages, request).initializePages();
    }

    private int getTotalPages(PagedCrateCreatorRequest request) {
        return new PagesCalculator(request).calculateTotalPages();
    }

}

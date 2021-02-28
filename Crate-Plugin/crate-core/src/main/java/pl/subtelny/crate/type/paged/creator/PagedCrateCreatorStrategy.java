package pl.subtelny.crate.type.paged.creator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.AbstractCrate;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.type.paged.PagedCrate;
import pl.subtelny.crate.creator.CrateCreatorStrategy;
import pl.subtelny.utilities.ColorUtil;

@Component
public class PagedCrateCreatorStrategy implements CrateCreatorStrategy<PagedCrateCreatorRequest> {

    @Override
    public Crate create(PagedCrateCreatorRequest request) {
        return constructPagedCrate(request);
    }

    @Override
    public CrateType getType() {
        return PagedCrate.TYPE;
    }

    private PagedCrate constructPagedCrate(PagedCrateCreatorRequest request) {
        PagedCrate pagedCrate = new PagedCrate(request.getCrateKey(), createInventory(request));
        AbstractCrate[] pages = getPages(pagedCrate, request);
        pagedCrate.setPages(pages);
        return pagedCrate;
    }

    private Inventory createInventory(PagedCrateCreatorRequest request) {
        String title = ColorUtil.color(request.getTitle());
        return Bukkit.createInventory(null, request.getInventorySize(), title);
    }

    private AbstractCrate[] getPages(PagedCrate pagedCrate, PagedCrateCreatorRequest request) {
        int totalPages = getTotalPages(request);
        return new PagesInitializer(pagedCrate, totalPages, request).initializePages();
    }

    private int getTotalPages(PagedCrateCreatorRequest request) {
        return new PagesCalculator(request).calculateTotalPages();
    }

}

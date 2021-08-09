package pl.subtelny.crate.type.paged;

import pl.subtelny.crate.BaseCrate;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.creator.CrateCreator;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.paged.PagedCrate;
import pl.subtelny.crate.api.item.paged.PagedCrateData;
import pl.subtelny.crate.api.listener.CrateListener;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.paged.PagedCratePrototype;
import pl.subtelny.crate.creator.DefaultCrateCreators;
import pl.subtelny.crate.inventory.CraftCrateInventory;
import pl.subtelny.crate.type.ACrateCreator;
import pl.subtelny.utilities.ColorUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PagedCrateCreator extends ACrateCreator<PagedCratePrototype> {

    private static final int NEW_PAGE_SIZE = 54;

    private static final int PREVIOUS_SLOT = 0;

    private static final int NEXT_SLOT = 8;

    @Override
    public Crate createCrate(CrateCreateRequest<PagedCratePrototype> request) {
        PagedCratePrototype pagedCratePrototype = request.getCratePrototype();
        PagedCrate crate = new PagedCrate(pagedCratePrototype.getCrateId());

        Stream<Crate> preConfiguredPages = pagedCratePrototype.getPageCratePrototypes()
                .stream()
                .map(cratePrototype -> createPage(cratePrototype, pagedCratePrototype, crate));

        Stream<Crate> newPages = IntStream.rangeClosed(0, totalEmptyPagesToCreate(pagedCratePrototype))
                .boxed()
                .map(integer -> createNewPage(pagedCratePrototype, crate));

        Stream.concat(preConfiguredPages, newPages).collect(Collectors.toList()).forEach(crate::addPage);
        fillControllerItemCrates(pagedCratePrototype, crate.getPages());
        pagedCratePrototype.getItemCratesToAdd().forEach(crate::addItemCrate);
        return crate;
    }

    private void fillControllerItemCrates(PagedCratePrototype pagedCratePrototype, List<Crate> totalPages) {
        int totalPagesSize = totalPages.size();
        for (int page = 0; page < totalPagesSize; page++) {
            Crate currentPage = totalPages.get(page);
            if (page > 0) {
                currentPage.setItemCrate(PREVIOUS_SLOT, pagedCratePrototype.getPreviousPageControllerItemCrate());
            }
            if (page < totalPagesSize - 1) {
                currentPage.setItemCrate(NEXT_SLOT, pagedCratePrototype.getNextPageControllerItemCrate());
            }
        }
    }

    private int totalEmptyPagesToCreate(PagedCratePrototype cratePrototype) {
        List<ItemCrate> itemCratesToAdd = cratePrototype.getItemCratesToAdd();
        if (itemCratesToAdd.isEmpty()) {
            return 0;
        }

        int page = 1;
        int itemsLeft = itemCratesToAdd.size();
        while (itemsLeft > 0) {
            int emptySlotsAtPage = emptySlotsAtPage(cratePrototype, page);
            itemsLeft -= emptySlotsAtPage;

            if (itemsLeft > 0) {
                itemsLeft += 2; //empty slots for controller items
                page++;
            }
        }

        int cratePrototypePagesSize = cratePrototype.getPageCratePrototypes().size();
        if (cratePrototypePagesSize >= page) {
            return 0;
        }
        return page - cratePrototypePagesSize;
    }

    private int emptySlotsAtPage(PagedCratePrototype cratePrototype, int page) {
        List<CratePrototype> pageCratePrototypes = cratePrototype.getPageCratePrototypes();
        int staticContentSize = cratePrototype.getStaticContent().size();
        if (page <= pageCratePrototypes.size()) {
            CratePrototype pageCratePrototype = pageCratePrototypes.get(page - 1);
            return pageCratePrototype.getSize() - (pageCratePrototype.getItemCrates().size() + staticContentSize);
        }
        return NEW_PAGE_SIZE - staticContentSize;
    }

    private Crate createPage(CratePrototype cratePrototype, PagedCratePrototype pagedCratePrototype, PagedCrate pagedCrate) {
        CrateData crateData = getCrateData(pagedCrate);
        Crate crate = createCrate(cratePrototype, crateData);
        pagedCratePrototype.getStaticContent().forEach(crate::setItemCrate);
        return crate;
    }

    private Crate createCrate(CratePrototype cratePrototype, CrateData crateData) {
        CrateCreator<CratePrototype> crateCreator = DefaultCrateCreators.getCrateCreator(cratePrototype.getClass());
        CrateCreateRequest<CratePrototype> request = CrateCreateRequest.builder(cratePrototype)
                .crateData(crateData)
                .build();
        return crateCreator.createCrate(request);
    }

    private Crate createNewPage(PagedCratePrototype cratePrototype, PagedCrate pagedCrate) {
        CrateData crateData = getCrateData(pagedCrate);
        return new BaseCrate(
                cratePrototype.getCrateId(),
                new CraftCrateInventory(ColorUtil.color(cratePrototype.getTitle()), NEW_PAGE_SIZE),
                crateData,
                CrateListener.EMPTY,
                Collections.emptyList(),
                cratePrototype.getStaticContent(),
                false
        );
    }

    private CrateData getCrateData(PagedCrate pagedCrate) {
        CrateData crateData = new CrateData();
        PagedCrateData pagedCrateData = PagedCrateData.of(crateData);
        pagedCrateData.setPagedCrate(pagedCrate);
        return crateData;
    }

}

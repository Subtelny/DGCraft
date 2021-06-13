package pl.subtelny.crate.type.paged;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.item.controller.PageControllerItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.*;

public class PagedCratePrototype extends CratePrototype {

    public final static CrateType TYPE = new CrateType("PAGED");

    private final Map<Integer, ItemCrate> staticContent;

    private final List<ItemCrate> itemCratesToAdd = new ArrayList<>();

    private final List<CratePrototype> pageCratePrototypes;

    private final PageControllerItemCrate nextPageControllerItemCrate;

    private final PageControllerItemCrate previousPageControllerItemCrate;

    public PagedCratePrototype(CrateId crateId,
                               Map<Integer, ItemCrate> staticContent,
                               List<CratePrototype> pageCratePrototypes,
                               PageControllerItemCrate nextPageControllerItemCrate,
                               PageControllerItemCrate previousPageControllerItemCrate) {
        super(crateId, 0, null, null, Collections.emptyMap(), false);
        this.pageCratePrototypes = pageCratePrototypes;
        this.nextPageControllerItemCrate = nextPageControllerItemCrate;
        this.previousPageControllerItemCrate = previousPageControllerItemCrate;
        this.staticContent = staticContent;
    }

    public Map<Integer, ItemCrate> getStaticContent() {
        return staticContent;
    }

    public List<ItemCrate> getItemCratesToAdd() {
        return itemCratesToAdd;
    }

    public List<CratePrototype> getPageCratePrototypes() {
        return pageCratePrototypes;
    }

    public PageControllerItemCrate getNextPageControllerItemCrate() {
        return nextPageControllerItemCrate;
    }

    public PageControllerItemCrate getPreviousPageControllerItemCrate() {
        return previousPageControllerItemCrate;
    }
}

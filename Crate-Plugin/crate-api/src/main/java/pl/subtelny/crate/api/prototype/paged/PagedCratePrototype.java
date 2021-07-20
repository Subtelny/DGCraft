package pl.subtelny.crate.api.prototype.paged;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.paged.PageControllerItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PagedCratePrototype extends CratePrototype {

    public final static CrateType TYPE = new CrateType("PAGED");

    private final Map<Integer, ItemCrate> staticContent;

    private final List<ItemCrate> itemCratesToAdd;

    private final List<CratePrototype> pageCratePrototypes;

    private final PageControllerItemCrate nextPageControllerItemCrate;

    private final PageControllerItemCrate previousPageControllerItemCrate;

    public PagedCratePrototype(CrateId crateId,
                               String title,
                               Map<Integer, ItemCrate> staticContent,
                               List<CratePrototype> pageCratePrototypes,
                               PageControllerItemCrate nextPageControllerItemCrate,
                               PageControllerItemCrate previousPageControllerItemCrate) {
        this(crateId, title, staticContent, pageCratePrototypes, new ArrayList<>(), nextPageControllerItemCrate, previousPageControllerItemCrate);
    }

    public PagedCratePrototype(CrateId crateId,
                               String title,
                               Map<Integer, ItemCrate> staticContent,
                               List<CratePrototype> pageCratePrototypes,
                               List<ItemCrate> itemCratesToAdd,
                               PageControllerItemCrate nextPageControllerItemCrate,
                               PageControllerItemCrate previousPageControllerItemCrate) {
        super(crateId, 0, title, null, Collections.emptyMap(), false);
        this.pageCratePrototypes = pageCratePrototypes;
        this.itemCratesToAdd = itemCratesToAdd;
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

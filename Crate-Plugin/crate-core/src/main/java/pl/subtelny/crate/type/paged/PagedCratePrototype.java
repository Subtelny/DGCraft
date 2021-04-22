package pl.subtelny.crate.type.paged;

import pl.subtelny.crate.CrateId;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.item.ItemCrate;
import pl.subtelny.crate.item.controller.PageControllerItemCrate;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.utilities.Validation;

import java.util.*;

public class PagedCratePrototype extends CratePrototype {

    public final static CrateType TYPE = new CrateType("PAGED");

    private final Map<Integer, ItemCrate> staticContent;

    private final Set<ItemCrate> itemCratesToAdd = new HashSet<>();

    private final List<CratePrototype> cratePrototypes;

    private final PageControllerItemCrate nextPageControllerItemCrate;

    private final PageControllerItemCrate previousPageControllerItemCrate;

    public PagedCratePrototype(CrateId crateId,
                               Map<Integer, ItemCrate> staticContent,
                               List<CratePrototype> cratePrototypes,
                               PageControllerItemCrate nextPageControllerItemCrate,
                               PageControllerItemCrate previousPageControllerItemCrate) {
        super(crateId, 0, null, null, Collections.emptyMap(), false);
        this.cratePrototypes = cratePrototypes;
        this.nextPageControllerItemCrate = nextPageControllerItemCrate;
        this.previousPageControllerItemCrate = previousPageControllerItemCrate;
        Validation.isTrue(crateId.getCrateType().equals(TYPE), "CrateId " + crateId.getInternal() + " not match to type " + TYPE.getValue());
        this.staticContent = staticContent;
    }

    public Map<Integer, ItemCrate> getStaticContent() {
        return staticContent;
    }

    public Set<ItemCrate> getItemCratesToAdd() {
        return itemCratesToAdd;
    }

    public List<CratePrototype> getCratePrototypes() {
        return cratePrototypes;
    }

    public PageControllerItemCrate getNextPageControllerItemCrate() {
        return nextPageControllerItemCrate;
    }

    public PageControllerItemCrate getPreviousPageControllerItemCrate() {
        return previousPageControllerItemCrate;
    }
}

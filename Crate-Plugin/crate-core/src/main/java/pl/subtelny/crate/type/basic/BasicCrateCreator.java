package pl.subtelny.crate.type.basic;

import pl.subtelny.crate.BaseCrate;
import pl.subtelny.crate.inventory.CraftCrateInventory;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.type.ACrateCreator;
import pl.subtelny.utilities.ColorUtil;

public class BasicCrateCreator extends ACrateCreator<BasicCratePrototype> {

    @Override
    public Crate createCrate(CrateCreateRequest<BasicCratePrototype> request) {
        BasicCratePrototype cratePrototype = request.getCratePrototype();
        return new BaseCrate(
                cratePrototype.getCrateId(),
                new CraftCrateInventory(ColorUtil.color(cratePrototype.getTitle()), cratePrototype.getSize()),
                request.getCrateData(),
                request.getCloseCrateListener(),
                getUseConditions(cratePrototype),
                cratePrototype.getItemCrates(),
                cratePrototype.isShared());
    }

}

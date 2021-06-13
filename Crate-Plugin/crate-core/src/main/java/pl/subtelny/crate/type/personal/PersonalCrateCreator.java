package pl.subtelny.crate.type.personal;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.inventory.CraftCrateInventory;
import pl.subtelny.crate.type.ACrateCreator;
import pl.subtelny.utilities.ColorUtil;

public class PersonalCrateCreator extends ACrateCreator<PersonalCratePrototype> {

    @Override
    public Crate createCrate(CrateCreateRequest<PersonalCratePrototype> request) {
        PersonalCratePrototype cratePrototype = request.getCratePrototype();
        return new PersonalCrate(
                cratePrototype.getCrateId(),
                new CraftCrateInventory(ColorUtil.color(cratePrototype.getTitle()), cratePrototype.getSize()),
                request.getCrateData(),
                request.getCloseCrateListener(),
                getUseConditions(cratePrototype),
                cratePrototype.getItemCrates()
        );
    }

}

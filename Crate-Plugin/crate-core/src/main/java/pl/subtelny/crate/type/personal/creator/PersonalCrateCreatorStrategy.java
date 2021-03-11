package pl.subtelny.crate.type.personal.creator;

import pl.subtelny.crate.api.ContentCrate;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.type.personal.PersonalCratePrototype;

public class PersonalCrateCreatorStrategy implements CrateCreatorStrategy<PersonalCrateCreatorRequest> {

    @Override
    public Crate create(PersonalCrateCreatorRequest request) {
        InventoryInfo inventory = createInventory(request);
        return new ContentCrate(request.getCrateKey(), request.getPermission(), inventory, request.getContent());
    }

    @Override
    public CrateType getType() {
        return PersonalCratePrototype.TYPE;
    }

    private InventoryInfo createInventory(PersonalCrateCreatorRequest request) {
        return InventoryInfo.of(request.getTitle(), request.getInventorySize());
    }

}

package pl.subtelny.crate.type.personal.creator;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.crate.type.personal.PersonalCratePrototype;

import java.util.Map;

public class PersonalCrateCreatorRequest extends CrateCreatorRequest {

    public PersonalCrateCreatorRequest(CrateKey crateKey, Map<Integer, ItemCrate> content, String title, int inventorySize, String permission) {
        super(PersonalCratePrototype.TYPE, crateKey, content, title, inventorySize, permission);
    }

}

package pl.subtelny.crate.type.personal.creator;

import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.creator.CrateCreatorRequest;

import java.util.Map;

public class PersonalCrateCreatorRequest extends CrateCreatorRequest {

    public PersonalCrateCreatorRequest(CrateKey crateKey, Map<Integer, ItemCrate> content, String title, int inventorySize, String permission) {
        super(crateKey, content, title, inventorySize, permission);
    }

}

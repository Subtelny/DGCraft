package pl.subtelny.crate.type.personal;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;

import java.util.Map;

public class PersonalCratePrototype extends CratePrototype {

    public final static CrateType TYPE = new CrateType("PERSONAL");

    public PersonalCratePrototype(CrateId crateId, int slots, String title, String permission, Map<Integer, ItemCrate> itemCrates) {
        super(crateId, slots, title, permission, itemCrates, false);
    }

}

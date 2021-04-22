package pl.subtelny.crate.type.personal;

import pl.subtelny.crate.CrateId;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.item.ItemCrate;
import pl.subtelny.utilities.Validation;

import java.util.Map;

public class PersonalCratePrototype extends CratePrototype {

    public final static CrateType TYPE = new CrateType("PERSONAL");

    public PersonalCratePrototype(CrateId crateId, int slots, String title, String permission, Map<Integer, ItemCrate> itemCrates) {
        super(crateId, slots, title, permission, itemCrates, false);
        Validation.isTrue(crateId.getCrateType().equals(TYPE), "CrateId " + crateId.getInternal() + " not match to type " + TYPE.getValue());
    }

}

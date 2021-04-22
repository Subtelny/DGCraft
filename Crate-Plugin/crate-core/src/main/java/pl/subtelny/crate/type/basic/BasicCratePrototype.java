package pl.subtelny.crate.type.basic;

import pl.subtelny.crate.CrateId;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.item.ItemCrate;
import pl.subtelny.utilities.Validation;

import java.util.Map;

public class BasicCratePrototype extends CratePrototype {

    public final static CrateType TYPE = new CrateType("BASIC");

    public BasicCratePrototype(CrateId crateId,
                               int slots,
                               String title,
                               String permission,
                               Map<Integer, ItemCrate> itemCrates,
                               boolean shared) {
        super(crateId, slots, title, permission, itemCrates, shared);
        Validation.isTrue(crateId.getCrateType().equals(TYPE), "CrateId " + crateId.getInternal() + " not match to type " + TYPE.getValue());
    }

}

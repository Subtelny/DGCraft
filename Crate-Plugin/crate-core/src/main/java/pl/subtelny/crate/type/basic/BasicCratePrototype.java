package pl.subtelny.crate.type.basic;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;

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
    }

}

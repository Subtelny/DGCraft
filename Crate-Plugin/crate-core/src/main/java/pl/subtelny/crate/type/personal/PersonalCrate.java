package pl.subtelny.crate.type.personal;

import org.bukkit.inventory.Inventory;
import pl.subtelny.crate.ContentCrate;
import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.utilities.Validation;

import java.util.Map;

public class PersonalCrate extends ContentCrate {

    public static CrateType TYPE = CrateType.of("PERSONAL");

    public PersonalCrate(CrateKey crateKey, String permission, Inventory inventory, Map<Integer, ItemCrate> itemCrates) {
        super(crateKey, permission, inventory, itemCrates);
        Validation.isTrue(TYPE.equals(crateKey.getType()), "CrateKey type is not PERSONAL");
    }

}

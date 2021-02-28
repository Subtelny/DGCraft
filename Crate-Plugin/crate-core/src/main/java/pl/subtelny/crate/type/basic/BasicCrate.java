package pl.subtelny.crate.type.basic;

import org.bukkit.inventory.Inventory;
import pl.subtelny.crate.ContentCrate;
import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.utilities.Validation;

import java.util.Map;

public class BasicCrate extends ContentCrate {

    public static CrateType TYPE = CrateType.of("BASIC");

    public BasicCrate(CrateKey crateKey,
                      String permission,
                      Map<Integer, ItemCrate> content,
                      Inventory inventory) {
        super(crateKey, permission, inventory, content);
        Validation.isTrue(TYPE.equals(crateKey.getType()), "CrateKey type is not BASIC");
    }

}

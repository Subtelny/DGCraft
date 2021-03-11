package pl.subtelny.crate.type.basic;

import pl.subtelny.crate.api.*;

import java.util.Map;

public class BasicCrate extends ContentCrate {

    public static CrateType TYPE = CrateType.of("BASIC");

    public BasicCrate(CrateKey crateKey,
                      String permission,
                      Map<Integer, ItemCrate> content,
                      InventoryInfo inventory) {
        super(crateKey, permission, inventory, content);
    }

}

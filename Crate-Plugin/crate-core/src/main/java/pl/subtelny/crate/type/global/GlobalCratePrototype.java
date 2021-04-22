package pl.subtelny.crate.type.global;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;

public class GlobalCratePrototype extends CratePrototype {

    public static CrateType TYPE = CrateType.of("GLOBAL");

    public GlobalCratePrototype(CrateKey crateKey,
                                CrateType crateType,
                                String title,
                                String permission,
                                int size,
                                Map<Integer, ItemCrate> content) {
        super(crateKey, crateType, title, permission, size, content);
    }

}

package pl.subtelny.crate.type.personal;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;

public class PersonalCratePrototype extends CratePrototype {

    public final static CrateType TYPE = CrateType.of("PERSONAL");

    public PersonalCratePrototype(CrateKey crateKey,
                                  String title,
                                  String permission,
                                  int size,
                                  Map<Integer, ItemCrate> content) {
        super(crateKey, TYPE, title, permission, size, content);
    }

}

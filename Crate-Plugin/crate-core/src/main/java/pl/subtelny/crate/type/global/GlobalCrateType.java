package pl.subtelny.crate.type.global;

import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.CrateType;

public final class GlobalCrateType {

    public static final CrateType TYPE = CrateType.of("GLOBAL");

    public static boolean isGlobal(CrateKey crateKey) {
        return TYPE.equals(crateKey.getType());
    }

}

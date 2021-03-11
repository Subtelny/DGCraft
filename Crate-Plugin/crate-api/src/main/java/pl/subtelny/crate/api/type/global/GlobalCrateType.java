package pl.subtelny.crate.api.type.global;

import pl.subtelny.crate.api.CrateType;

public final class GlobalCrateType {

    public static final CrateType TYPE = CrateType.of("GLOBAL");

    public static boolean isGlobal(CrateType crateType) {
        return TYPE.equals(crateType);
    }

}

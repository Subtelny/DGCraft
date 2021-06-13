package pl.subtelny.crate.api;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class CrateId extends CompoundIdentity {

    private CrateId(String id) {
        super(values(id));
    }

    private CrateId(String id, String prefix) {
        super(values(id, prefix));
    }

    public static CrateId of(String id, String prefix) {
        return new CrateId(id, prefix);
    }

    public static CrateId of(String id) {
        return new CrateId(id);
    }

    public String getValue() {
        return getAtPosition(0);
    }

}

package pl.subtelny.crate;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class CrateId extends CompoundIdentity {

    private CrateId(String id, String crateType) {
        super(values(id, crateType));
    }

    public static CrateId of(String id, CrateType crateType) {
        return new CrateId(id, crateType.getValue());
    }

    public CrateType getCrateType() {
        return new CrateType(getAtPosition(1));
    }

    public String getValue() {
        return getAtPosition(0);
    }

}

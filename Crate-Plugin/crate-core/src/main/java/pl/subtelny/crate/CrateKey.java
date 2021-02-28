package pl.subtelny.crate;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class CrateKey extends CompoundIdentity {

    private static final int TYPE_POSITION = 0;

    private static final int ID_POSITION = 1;

    private CrateKey(String type, String id) {
        super(type + "@" + id);
    }

    public static CrateKey of(CrateType type, String id) {
        return CrateKey.of(type, id);
    }

    public CrateType getType() {
        return CrateType.of(getAtPosition(TYPE_POSITION));
    }

    public String getIdentity() {
        return getAtPosition(ID_POSITION);
    }

}

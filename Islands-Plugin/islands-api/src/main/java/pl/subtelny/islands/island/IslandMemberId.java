package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class IslandMemberId extends CompoundIdentity {

    private IslandMemberId(String... values) {
        super(values(values));
    }

    public static IslandMemberId of(String... values) {
        return new IslandMemberId(values);
    }

    public static IslandMemberId of(String internal) {
        return new IslandMemberId(internal.split(CompoundIdentity.SEPARATOR));
    }

    public String getType() {
        return getAtPosition(0);
    }

    public String getValue() {
        return getAtPosition(1);
    }
}

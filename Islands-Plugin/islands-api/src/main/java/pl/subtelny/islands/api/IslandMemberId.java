package pl.subtelny.islands.api;

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

    public IslandMemberType getType() {
        return new IslandMemberType(getAtPosition(0));
    }

    public String getValue() {
        return getAtPosition(1);
    }
}

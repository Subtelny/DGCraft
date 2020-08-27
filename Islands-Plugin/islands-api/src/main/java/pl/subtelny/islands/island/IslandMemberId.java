package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class IslandMemberId extends CompoundIdentity {

    private final static String ISLANDER_VALUE = "ISLANDER";

    public IslandMemberId(String value, String secondValue) {
        super(values(value, secondValue));
    }

    public static IslandMemberId of(IslanderId islanderId) {
        return new IslandMemberId(ISLANDER_VALUE, islanderId.getInternal().toString());
    }
}

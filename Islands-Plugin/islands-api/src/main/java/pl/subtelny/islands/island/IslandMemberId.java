package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class IslandMemberId extends CompoundIdentity {

    private final static String ISLANDER_VALUE = "ISLANDER";

    private IslandMemberId(String... values) {
        super(values(values));
    }

    public static IslandMemberId of(IslanderId islanderId) {
        return new IslandMemberId(ISLANDER_VALUE, islanderId.getInternal().toString());
    }

    public static IslandMemberId of(String internal) {
        return new IslandMemberId(internal.split(CompoundIdentity.SEPARATOR));
    }
}

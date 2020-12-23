package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.BasicIdentity;

public class IslandType extends BasicIdentity<String> {

    public IslandType(String id) {
        super(id);
    }

    public static IslandType of(String islandType) {
        return new IslandType(islandType);
    }

}

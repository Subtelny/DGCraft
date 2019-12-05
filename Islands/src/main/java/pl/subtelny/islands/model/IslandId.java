package pl.subtelny.islands.model;

import pl.subtelny.identity.BasicIdentity;

public class IslandId extends BasicIdentity<Integer> {

    public IslandId(Integer id) {
        super(id);
    }

    public static IslandId of(Integer id) {
        return new IslandId(id);
    }

}

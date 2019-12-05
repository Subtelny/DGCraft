package pl.subtelny.islands.model;

import pl.subtelny.identity.BasicIdentity;

public class GuildId extends BasicIdentity<Integer> {

    public GuildId(Integer id) {
        super(id);
    }

    public static IslandId of(Integer id) {
        return new IslandId(id);
    }

}

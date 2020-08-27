package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.BasicIdentity;

public class IslandMemberRank extends BasicIdentity<String> {

    private final boolean unique;

    public IslandMemberRank(String id, boolean unique) {
        super(id);
        this.unique = unique;
    }

    public boolean isUnique() {
        return unique;
    }
}

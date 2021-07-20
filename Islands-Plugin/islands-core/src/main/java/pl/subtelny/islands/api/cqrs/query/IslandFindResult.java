package pl.subtelny.islands.api.cqrs.query;

import pl.subtelny.islands.api.Island;
import pl.subtelny.result.OptionalResult;

public class IslandFindResult extends OptionalResult<Island> {

    public final static IslandFindResult NOT_ISLAND_WORLD = new IslandFindResult(null, true);

    private final boolean notIslandWorld;

    private IslandFindResult(Island result, boolean notIslandWorld) {
        super(result);
        this.notIslandWorld = notIslandWorld;
    }

    public static IslandFindResult of(Island result) {
        return new IslandFindResult(result, false);
    }

    public static IslandFindResult notIslandWorld() {
        return NOT_ISLAND_WORLD;
    }

    public boolean isNotIslandWorld() {
        return notIslandWorld;
    }

}

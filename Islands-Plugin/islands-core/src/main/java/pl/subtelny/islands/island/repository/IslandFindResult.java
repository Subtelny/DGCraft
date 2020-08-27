package pl.subtelny.islands.island.repository;

import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.result.OptionalResult;

public class IslandFindResult extends OptionalResult<AbstractIsland> {

    public final static IslandFindResult NOT_ISLAND_WORLD = notIslandWorld();

    private final boolean notIslandWorld;

    private IslandFindResult(AbstractIsland result, boolean notIslandWorld) {
        super(result);
        this.notIslandWorld = notIslandWorld;
    }

    public static IslandFindResult of(AbstractIsland result) {
        return new IslandFindResult(result, false);
    }

    public static IslandFindResult notIslandWorld() {
        return new IslandFindResult(null, true);
    }

    public boolean isNotIslandWorld() {
        return notIslandWorld;
    }

}

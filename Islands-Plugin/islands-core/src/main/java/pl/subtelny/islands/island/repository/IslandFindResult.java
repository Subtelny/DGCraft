package pl.subtelny.islands.island.repository;

import pl.subtelny.islands.islander.model.Island;
import pl.subtelny.result.CompletableResult;
import pl.subtelny.result.OptionalResult;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class IslandFindResult extends OptionalResult<Island> {

    public final static IslandFindResult NOT_ISLAND_WORLD = notIslandWorld();

    private final boolean notIslandWorld;

    private IslandFindResult(Island result, boolean notIslandWorld) {
        super(result);
        this.notIslandWorld = notIslandWorld;
    }

    public static IslandFindResult of(Island result) {
        return new IslandFindResult(result, false);
    }

    public static IslandFindResult notIslandWorld() {
        return new IslandFindResult(null, true);
    }

    public boolean isNotIslandWorld() {
        return notIslandWorld;
    }

}

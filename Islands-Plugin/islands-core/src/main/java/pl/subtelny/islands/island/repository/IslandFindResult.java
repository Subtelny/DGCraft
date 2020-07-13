package pl.subtelny.islands.island.repository;

import pl.subtelny.islands.islander.model.Island;
import pl.subtelny.result.CompletableResult;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class IslandFindResult extends CompletableResult<Optional<Island>> {

    public final static IslandFindResult NOT_ISLAND_WORLD = new IslandFindResult(null);

    public IslandFindResult(CompletableFuture<Optional<Island>> result) {
        super(result);
    }

    public boolean isNotIslandWorld() {
        return getResult() == null;
    }

}

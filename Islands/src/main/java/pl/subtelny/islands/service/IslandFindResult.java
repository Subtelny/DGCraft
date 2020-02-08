package pl.subtelny.islands.service;

import pl.subtelny.islands.model.island.Island;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class IslandFindResult {

    public final static IslandFindResult NOT_ISLAND_WORLD = new IslandFindResult();

    private CompletableFuture<Optional<Island>> island;

    protected IslandFindResult() {
    }

    public IslandFindResult(CompletableFuture<Optional<Island>> island) {
        this.island = island;
    }

    public CompletableFuture<Optional<Island>> getIsland() {
        return island;
    }

    public boolean isNotIslandWorld() {
    	return island == null;
	}

	public boolean isLoaded() {
        if(island == null) {
            return false;
        }
        return island.isDone();
    }

    public boolean isLoading() {
        if (island == null) {
            return false;
        }
        return !island.isDone();
    }

}

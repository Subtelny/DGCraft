package pl.subtelny.islands.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import pl.subtelny.islands.model.island.Island;

public class IslandFindResult {

	private CompletableFuture<Optional<Island>> island;

	public IslandFindResult() {
	}

	public IslandFindResult(CompletableFuture<Optional<Island>> island) {
		this.island = island;
	}

	public CompletableFuture<Optional<Island>> getIsland() {
		return island;
	}

	public boolean isEmpty() {
		return island == null;
	}

	public boolean isLoading() {
		if(isEmpty()) {
			return false;
		}
		CompletableFuture<Optional<Island>> islandOpt = getIsland();
		return !islandOpt.isDone();
	}
}

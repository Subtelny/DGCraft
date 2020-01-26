package pl.subtelny.islands.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import pl.subtelny.islands.model.island.Island;

public class IslandFindResult {

	private CompletableFuture<Optional<Island>> island;

	private boolean isLoading;

	public IslandFindResult(CompletableFuture<Optional<Island>> island, boolean isLoading) {
		this.island = island;
		this.isLoading = isLoading;
	}

	public CompletableFuture<Optional<Island>> getIsland() {
		return island;
	}

	public boolean isLoading() {
		return isLoading;
	}
}

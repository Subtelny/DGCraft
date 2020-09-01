package pl.subtelny.islands.island.creator;

import pl.subtelny.islands.island.model.AbstractIsland;

import java.util.concurrent.CompletableFuture;

public interface IslandCreator<T extends AbstractIsland, R extends IslandCreateRequest> {

    CompletableFuture<T> create(R request);

}

package pl.subtelny.islands.island.repository;

import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;

import java.util.Optional;

public interface IslandRepositoryConnector<T extends Island> {

    Optional<T> loadIsland(IslandId islandId);

    T updateIsland(T island);

    IslandType getType();

}

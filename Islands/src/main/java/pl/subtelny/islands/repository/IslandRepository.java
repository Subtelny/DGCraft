package pl.subtelny.islands.repository;

import java.util.Optional;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;

@Component
public class IslandRepository {

	public Island getIsland(IslandId islandId) {
		return null;
	}

	public Optional<Island> findIsland(IslandId islandId) {
		return Optional.empty();
	}



}

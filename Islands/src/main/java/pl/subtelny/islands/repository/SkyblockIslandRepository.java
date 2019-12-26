package pl.subtelny.islands.repository;

import java.util.Optional;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.SkyblockIsland;

@Component
public class SkyblockIslandRepository {

	private final IslandRepository islandRepository;

	@Autowired
	public SkyblockIslandRepository(IslandRepository islandRepository) {
		this.islandRepository = islandRepository;
	}

	public SkyblockIsland getIsland(IslandCoordinates islandCoordinates) {
		return null;
	}

	public Optional<SkyblockIsland> findIsland(IslandCoordinates islandCoordinates) {
		return Optional.empty();
	}

	public void saveIsland(SkyblockIsland island) {

	}

}

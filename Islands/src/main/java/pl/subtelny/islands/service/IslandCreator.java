package pl.subtelny.islands.service;

import java.util.Optional;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.repository.IslandRepository;
import pl.subtelny.islands.repository.SkyblockIslandStorage;

@Component
public class IslandCreator {

	private final IslandRepository islandRepository;

	private final SkyblockIslandStorage skyblockIslandStorage;

	@Autowired
	public IslandCreator(IslandRepository islandRepository, SkyblockIslandStorage skyblockIslandStorage) {
		this.islandRepository = islandRepository;
		this.skyblockIslandStorage = skyblockIslandStorage;
	}

	public Island createIsland(SkyblockIslandCreateRequest request) {
		Optional<IslandCoordinates> islandCoordinates = skyblockIslandStorage.nextFreeIslandCoordinates();


		return null;
	}

}

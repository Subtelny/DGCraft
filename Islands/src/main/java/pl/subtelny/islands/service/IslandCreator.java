package pl.subtelny.islands.service;

import java.util.Optional;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.repository.SkyblockIslandRepository;

@Component
public class IslandCreator {

	private final SkyblockIslandRepository skyblockIslandRepository;

	@Autowired
	public IslandCreator(SkyblockIslandRepository skyblockIslandRepository) {
		this.skyblockIslandRepository = skyblockIslandRepository;
	}

	public Island createIsland(SkyblockIslandCreateRequest request) {



		return null;
	}

}
package pl.subtelny.islands.service;

import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.repository.island.SkyblockIslandRepository;

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

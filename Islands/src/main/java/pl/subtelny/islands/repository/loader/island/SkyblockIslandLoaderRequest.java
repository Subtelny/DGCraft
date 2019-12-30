package pl.subtelny.islands.repository.loader.island;

import java.util.Optional;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;

public class SkyblockIslandLoaderRequest {

	private IslandId islandId;

	private IslandCoordinates islandCoordinates;

	public SkyblockIslandLoaderRequest(IslandId islandId) {
		this.islandId = islandId;
	}

	public SkyblockIslandLoaderRequest(IslandCoordinates islandCoordinates) {
		this.islandCoordinates = islandCoordinates;
	}

	public Optional<IslandId> getIslandId() {
		return Optional.ofNullable(islandId);
	}

	public Optional<IslandCoordinates> getIslandCoordinates() {
		return Optional.ofNullable(islandCoordinates);
	}

}

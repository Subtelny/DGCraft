package pl.subtelny.islands.repository.loader.island;

import java.util.UUID;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandCoordinates;

public class SkyblockIslandData extends IslandData {

	private IslandCoordinates islandCoordinates;

	private UUID owner;

	public SkyblockIslandData() {
	}

	public SkyblockIslandData(IslandCoordinates islandCoordinates, UUID owner, IslandData islandData) {
		super(islandData.getIslandId(), IslandType.SKYBLOCK, islandData.getCreatedDate(), islandData.getSpawn());
		this.islandCoordinates = islandCoordinates;
		this.owner = owner;
	}

	public IslandCoordinates getIslandCoordinates() {
		return islandCoordinates;
	}

	public void setIslandCoordinates(IslandCoordinates islandCoordinates) {
		this.islandCoordinates = islandCoordinates;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}
}

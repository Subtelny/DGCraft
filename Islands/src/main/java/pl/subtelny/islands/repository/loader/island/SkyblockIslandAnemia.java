package pl.subtelny.islands.repository.loader.island;

import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandCoordinates;

public class SkyblockIslandAnemia extends IslandAnemia {

	private IslandCoordinates islandCoordinates;

	private AccountId owner;

	private int extendLevel;

	public SkyblockIslandAnemia() {
	}

	public SkyblockIslandAnemia(IslandAnemia islandAnemia, IslandCoordinates islandCoordinates, AccountId owner, int extendLevel) {
		super(islandAnemia.getIslandId(), IslandType.SKYBLOCK, islandAnemia.getCreatedDate(), islandAnemia.getSpawn());
		this.islandCoordinates = islandCoordinates;
		this.owner = owner;
		this.extendLevel = extendLevel;
	}

	public IslandCoordinates getIslandCoordinates() {
		return islandCoordinates;
	}

	public void setIslandCoordinates(IslandCoordinates islandCoordinates) {
		this.islandCoordinates = islandCoordinates;
	}

	public AccountId getOwner() {
		return owner;
	}

	public void setOwner(AccountId owner) {
		this.owner = owner;
	}

	public int getExtendLevel() {
		return extendLevel;
	}

	public void setExtendLevel(int extendLevel) {
		this.extendLevel = extendLevel;
	}
}

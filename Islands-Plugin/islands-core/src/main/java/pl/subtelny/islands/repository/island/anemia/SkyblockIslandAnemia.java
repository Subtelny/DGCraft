package pl.subtelny.islands.repository.island.anemia;

import java.time.LocalDateTime;
import org.bukkit.Location;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;

public class SkyblockIslandAnemia extends IslandAnemia {

	private IslandCoordinates islandCoordinates;

	private AccountId owner;

	private int extendLevel;

	private int points;

	public SkyblockIslandAnemia() {
	}

	public SkyblockIslandAnemia(IslandId islandId, LocalDateTime createdDate, Location spawn,
								IslandCoordinates islandCoordinates, AccountId owner, int extendLevel, int points) {
		super(islandId, createdDate, spawn);
		this.islandCoordinates = islandCoordinates;
		this.owner = owner;
		this.extendLevel = extendLevel;
		this.points = points;
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public IslandType getIslandType() {
		return IslandType.SKYBLOCK;
	}
}

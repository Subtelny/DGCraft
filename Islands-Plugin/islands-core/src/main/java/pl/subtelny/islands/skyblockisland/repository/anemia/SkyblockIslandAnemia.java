package pl.subtelny.islands.skyblockisland.repository.anemia;

import java.time.LocalDateTime;
import org.bukkit.Location;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;

public class SkyblockIslandAnemia extends IslandAnemia {

	private IslandCoordinates islandCoordinates;

	private IslanderId owner;

	private int extendLevel;

	private int points;

	public SkyblockIslandAnemia() {
	}

	public SkyblockIslandAnemia(SkyblockIslandId islandId, LocalDateTime createdDate, Location spawn,
								IslandCoordinates islandCoordinates, IslanderId owner, int extendLevel, int points) {
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

	public IslanderId getOwner() {
		return owner;
	}

	public void setOwner(IslanderId owner) {
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
	public SkyblockIslandId getIslandId() {
		return (SkyblockIslandId) super.getIslandId();
	}

	@Override
	public IslandType getIslandType() {
		return IslandType.SKYBLOCK;
	}
}

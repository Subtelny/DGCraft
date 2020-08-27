package pl.subtelny.islands.skyblockisland.repository.anemia;

import org.bukkit.Location;
import pl.subtelny.islands.island.repository.anemia.IslandAnemia;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;

import java.time.LocalDateTime;

public class SkyblockIslandAnemia extends IslandAnemia {

	private IslandCoordinates islandCoordinates;

	private int extendLevel;

	public SkyblockIslandAnemia() {
	}

	public SkyblockIslandAnemia(SkyblockIslandId islandId, LocalDateTime createdDate, Location spawn,
								IslandCoordinates islandCoordinates, int extendLevel, int points) {
		super(islandId, createdDate, spawn, points);
		this.islandCoordinates = islandCoordinates;
		this.extendLevel = extendLevel;
	}

	public IslandCoordinates getIslandCoordinates() {
		return islandCoordinates;
	}

	public void setIslandCoordinates(IslandCoordinates islandCoordinates) {
		this.islandCoordinates = islandCoordinates;
	}

	public int getExtendLevel() {
		return extendLevel;
	}

	public void setExtendLevel(int extendLevel) {
		this.extendLevel = extendLevel;
	}

	@Override
	public SkyblockIslandId getIslandId() {
		return (SkyblockIslandId) super.getIslandId();
	}

}

package pl.subtelny.islands.island.skyblockisland.repository.anemia;

import org.bukkit.Location;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.repository.anemia.IslandAnemia;
import pl.subtelny.islands.island.IslandCoordinates;

import java.time.LocalDateTime;

public class SkyblockIslandAnemia extends IslandAnemia {

	private IslandCoordinates islandCoordinates;

	private int extendLevel;

	SkyblockIslandAnemia() {
	}

	public SkyblockIslandAnemia(Location spawn, IslandCoordinates islandCoordinates, IslandType islandType) {
		super(null, LocalDateTime.now(), spawn, 0);
		this.islandCoordinates = islandCoordinates;
	}

	public SkyblockIslandAnemia(IslandId islandId, LocalDateTime createdDate, Location spawn,
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

}

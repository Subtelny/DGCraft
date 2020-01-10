package pl.subtelny.islands.repository.loader.island;

import org.bukkit.Location;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandId;

import java.time.LocalDateTime;

public class IslandAnemia {

	private IslandId islandId;

	private IslandType islandType;

	private LocalDateTime createdDate;

	private Location spawn;

	public IslandAnemia() {
	}

	public IslandAnemia(IslandId islandId, IslandType islandType, LocalDateTime createdDate, Location spawn) {
		this.islandId = islandId;
		this.islandType = islandType;
		this.createdDate = createdDate;
		this.spawn = spawn;
	}

	public IslandId getIslandId() {
		return islandId;
	}

	public void setIslandId(IslandId islandId) {
		this.islandId = islandId;
	}

	public IslandType getIslandType() {
		return islandType;
	}

	public void setIslandType(IslandType islandType) {
		this.islandType = islandType;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
}

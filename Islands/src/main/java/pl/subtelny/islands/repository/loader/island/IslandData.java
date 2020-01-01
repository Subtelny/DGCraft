package pl.subtelny.islands.repository.loader.island;

import java.time.LocalDate;
import org.bukkit.Location;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandId;

public class IslandData {

	private IslandId islandId;

	private IslandType islandType;

	private LocalDate createdDate;

	private Location spawn;

	public IslandData() {
	}

	public IslandData(IslandId islandId, IslandType islandType, LocalDate createdDate, Location spawn) {
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

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
}

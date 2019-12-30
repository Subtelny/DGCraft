package pl.subtelny.islands.repository.loader.island;

import java.time.LocalDate;
import org.bukkit.Location;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;

public class SkyblockIslandData {

	private IslandId islandId;

	private IslandCoordinates islandCoordinates;

	private LocalDate createdDate;

	private int owner;

	private Location spawn;

	public SkyblockIslandData() {
	}

	public SkyblockIslandData(IslandId islandId, IslandCoordinates islandCoordinates, LocalDate createdDate, int owner, Location spawn) {
		this.islandId = islandId;
		this.islandCoordinates = islandCoordinates;
		this.createdDate = createdDate;
		this.owner = owner;
		this.spawn = spawn;
	}

	public IslandId getIslandId() {
		return islandId;
	}

	public void setIslandId(IslandId islandId) {
		this.islandId = islandId;
	}

	public IslandCoordinates getIslandCoordinates() {
		return islandCoordinates;
	}

	public void setIslandCoordinates(IslandCoordinates islandCoordinates) {
		this.islandCoordinates = islandCoordinates;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

}

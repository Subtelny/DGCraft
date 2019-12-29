package pl.subtelny.islands.repository.loader;

import java.time.LocalDate;
import java.util.List;
import org.bukkit.Location;
import pl.subtelny.islands.model.island.IslandCoordinates;

public class SkyblockIslandRecordResult {

	private final IslandCoordinates islandCoordinates;

	private final LocalDate createdDate;

	private final int owner;

	private final Location spawn;

	private final List<IslandMemberResult> islandMembers;

	SkyblockIslandRecordResult(IslandCoordinates islandCoordinates,
			LocalDate createdDate,
			int owner,
			Location spawn,
			List<IslandMemberResult> islandMembers) {
		this.islandCoordinates = islandCoordinates;
		this.createdDate = createdDate;
		this.owner = owner;
		this.spawn = spawn;
		this.islandMembers = islandMembers;
	}

	public IslandCoordinates getIslandCoordinates() {
		return islandCoordinates;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public int getOwner() {
		return owner;
	}

	public Location getSpawn() {
		return spawn;
	}

	public List<IslandMemberResult> getIslandMembers() {
		return islandMembers;
	}
}

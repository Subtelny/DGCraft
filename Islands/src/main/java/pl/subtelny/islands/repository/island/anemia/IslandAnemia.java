package pl.subtelny.islands.repository.island.anemia;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.IslandId;

import java.time.LocalDateTime;

public abstract class IslandAnemia {

	private IslandId islandId;

	private LocalDateTime createdDate;

	private Location spawn;

	public IslandAnemia() {
	}

	public IslandAnemia(IslandId islandId, LocalDateTime createdDate, Location spawn) {
		this.islandId = islandId;
		this.createdDate = createdDate;
		this.spawn = spawn;
	}

	public IslandId getIslandId() {
		return islandId;
	}

	public void setIslandId(IslandId islandId) {
		this.islandId = islandId;
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

	public abstract IslandType getIslandType();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		IslandAnemia that = (IslandAnemia) o;

		return new EqualsBuilder()
				.append(islandId, that.islandId)
				.append(createdDate, that.createdDate)
				.append(spawn, that.spawn)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(islandId)
				.append(createdDate)
				.append(spawn)
				.toHashCode();
	}
}

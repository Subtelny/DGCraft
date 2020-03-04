package pl.subtelny.islands.model.island;

import pl.subtelny.islands.model.islander.Islander;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import pl.subtelny.utils.LocationUtil;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

import java.time.LocalDateTime;

public abstract class Island {

	protected IslandId islandId;

	protected LocalDateTime createdDate;

	protected Location spawn;

	protected Cuboid cuboid;

	public Island(Cuboid cuboid) {
		this.cuboid = cuboid;
	}

	public void changeSpawn(Location spawn) {
		if (!LocationUtil.isSafeForPlayer(spawn)) {
			throw new ValidationException("Block under spawn have to be a solid material");
		}
		this.spawn = spawn;
	}

	public abstract boolean isInIsland(Islander islander);

	public abstract void recalculateSpawn();

	public abstract IslandType getIslandType();

	public IslandId getIslandId() {
		return islandId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public Location getSpawn() {
		return spawn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Island island = (Island) o;

		return new EqualsBuilder()
				.append(islandId, island.islandId)
				.append(createdDate, island.createdDate)
				.append(spawn, island.spawn)
				.append(cuboid, island.cuboid)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(islandId)
				.append(createdDate)
				.append(spawn)
				.append(cuboid)
				.toHashCode();
	}
}

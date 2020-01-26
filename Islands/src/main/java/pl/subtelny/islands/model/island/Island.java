package pl.subtelny.islands.model.island;

import java.time.LocalDateTime;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.islands.utils.LocationUtil;
import pl.subtelny.model.SynchronizedEntity;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public abstract class Island extends SynchronizedEntity {

	private final IslandAnemia islandAnemia;

	protected Cuboid cuboid;

	public Island(IslandAnemia islandAnemia, Cuboid cuboid) {
		this.cuboid = cuboid;
		this.islandAnemia = islandAnemia;
	}

	public void changeSpawn(Location spawn) {
		if (!LocationUtil.isSafeForPlayer(spawn)) {
			throw new ValidationException("Block under spawn have to be a solid material");
		}
		this.islandAnemia.setSpawn(spawn);
	}

	public abstract void recalculateSpawn();

	public abstract IslandType getIslandType();

	public IslandId getIslandId() {
		return islandAnemia.getIslandId();
	}

	public LocalDateTime getCreatedDate() {
		return islandAnemia.getCreatedDate();
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public Location getSpawn() {
		return islandAnemia.getSpawn();
	}

	protected IslandAnemia getIslandAnemia() {
		return islandAnemia;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Island island = (Island) o;

		return new EqualsBuilder()
				.append(islandAnemia.getIslandId(), island.getIslandId())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(islandAnemia.getIslandId())
				.toHashCode();
	}
}

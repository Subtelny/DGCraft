package pl.subtelny.islands.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.utils.LocationUtil;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public abstract class Island {

	protected Cuboid cuboid;

	private Location spawn;

	private final LocalDate createdDate;

	private final IslandId islandId;

	public Island(IslandId islandId, Cuboid cuboid, LocalDate createdDate) {
		this.islandId = islandId;
		this.cuboid = cuboid;
		this.createdDate = createdDate;
	}

	public boolean isInIsland(IslandMember islandMember) {
		if (getMembers().contains(islandMember)) {
			return true;
		}
		Optional<IslandMember> ownerOpt = getOwner();
		return ownerOpt.map(member -> member.equals(islandMember)).orElse(false);
	}

	public IslandId getIslandId() {
		return islandId;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void changeSpawn(Location spawn) {
		if (!LocationUtil.isSafeForPlayer(spawn)) {
			throw new ValidationException("Block under spawn have to be a solid material");
		}
		this.spawn = spawn;
	}

	public abstract boolean canBuild(Player player);

	public abstract boolean isInIsland(Player player);

	public abstract void recalculateSpawn();

	public abstract Optional<IslandMember> getOwner();

	public abstract Set<IslandMember> getMembers();

	public abstract void changeOwner(IslandMember owner);

	public abstract void addMember(IslandMember member);

	public abstract void removeMember(IslandMember member);

	public abstract IslandType getIslandType();

}

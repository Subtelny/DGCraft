package pl.subtelny.islands.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
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

	public void changeSpawn(Location spawn) {
		if (!LocationUtil.isSafeForPlayer(spawn)) {
			throw new ValidationException("Block under spawn have to be a solid material");
		}
		this.spawn = spawn;
	}

	public boolean canInteract(Entity entity, Entity toInteract) {
		if (entity instanceof Player) {
			Player attackerPlayer = (Player) entity;
			return isInIsland(attackerPlayer);
		}
		return true;
	}

	public boolean canHit(Entity attacker, Entity victim) {
		if (attacker instanceof Player) {
			Player attackerPlayer = (Player) attacker;
			boolean attackersIsInIsland = isInIsland(attackerPlayer);
			if (victim instanceof Player) {
				Player victimPlayer = (Player) victim;
				boolean victimIsInIsland = isInIsland(victimPlayer);
				if (attackersIsInIsland && victimIsInIsland) {
					return false;
				}
			}
			return attackersIsInIsland;
		}
		return true;
	}

	public boolean canBuild(Player player) {
		return isInIsland(player);
	}

	public abstract boolean isInIsland(Player player);

	public abstract void recalculateSpawn();

	public abstract Optional<IslandMember> getOwner();

	public abstract Set<IslandMember> getMembers();

	public abstract void changeOwner(IslandMember owner);

	public abstract void addMember(IslandMember member);

	public abstract void removeMember(IslandMember member);

	public abstract IslandType getIslandType();

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
				.append(islandId, island.islandId)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(islandId)
				.toHashCode();
	}
}

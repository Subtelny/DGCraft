package pl.subtelny.islands.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.island.IslandAnemia;
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
		this.islandAnemia.setSpawn(spawn);
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

	public IslandAnemia getIslandAnemia() {
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

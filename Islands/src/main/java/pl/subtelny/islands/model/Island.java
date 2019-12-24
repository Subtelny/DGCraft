package pl.subtelny.islands.model;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.util.Set;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.utils.cuboid.Cuboid;

public abstract class Island {

	protected IslandMember owner;

	protected Set<IslandMember> members = Sets.newHashSet();

	protected Cuboid cuboid;

	private final LocalDate createdDate;

	private final IslandId islandId;

	public Island(IslandId islandId, Cuboid cuboid, LocalDate createdDate) {
		this.islandId = islandId;
		this.cuboid = cuboid;
		this.createdDate = createdDate;
	}

	public IslandId getIslandId() {
		return islandId;
	}

	public IslandMember getOwner() {
		return owner;
	}

	public Set<IslandMember> getMembers() {
		return Sets.newHashSet(members);
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public abstract void changeOwner(IslandMember owner);

	public abstract void addMember(IslandMember member);

	public abstract void removeMember(IslandMember member);

	public abstract IslandType getIslandType();

}

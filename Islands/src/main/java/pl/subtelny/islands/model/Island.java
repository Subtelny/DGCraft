package pl.subtelny.islands.model;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.util.Set;
import pl.subtelny.utils.cuboid.Cuboid;

public abstract class Island {

	private final IslandId islandId;

	protected IslandMember owner;

	protected Set<IslandMember> members = Sets.newHashSet();

	private final Cuboid cuboid;

	private final LocalDate createdDate;

	protected Island(IslandId islandId, Cuboid cuboid, LocalDate createdDate) {
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

	abstract void changeOwner(IslandMember owner);

	abstract void addMember(IslandMember member);

	abstract void removeMember(IslandMember member);

}

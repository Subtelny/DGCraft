package pl.subtelny.islands.repository.loader;

import pl.subtelny.islands.model.IslandMemberType;

public class IslandMemberResult {

	private final String id;

	private final IslandMemberType islandMemberType;

	public IslandMemberResult(String id, IslandMemberType islandMemberType) {
		this.id = id;
		this.islandMemberType = islandMemberType;
	}

	public String getId() {
		return id;
	}

	public IslandMemberType getIslandMemberType() {
		return islandMemberType;
	}
}

package pl.subtelny.islands.repository.island.anemia;

import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.island.IslandId;

public class IslandMemberAnemia {

	private IslandId islandId;

	private String id;

	private IslandMemberType islandMemberType;

	public IslandMemberAnemia(IslandId islandId, String id, IslandMemberType islandMemberType) {
		this.islandId = islandId;
		this.id = id;
		this.islandMemberType = islandMemberType;
	}

	public IslandId getIslandId() {
		return islandId;
	}

	public void setIslandId(IslandId islandId) {
		this.islandId = islandId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IslandMemberType getIslandMemberType() {
		return islandMemberType;
	}

	public void setIslandMemberType(IslandMemberType islandMemberType) {
		this.islandMemberType = islandMemberType;
	}
}

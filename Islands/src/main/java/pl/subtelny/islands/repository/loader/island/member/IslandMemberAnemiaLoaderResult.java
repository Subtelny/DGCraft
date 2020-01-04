package pl.subtelny.islands.repository.loader.island.member;

import java.util.List;

public class IslandMemberAnemiaLoaderResult {

	private List<IslandMemberAnemia> islandMembersData;

	public IslandMemberAnemiaLoaderResult(List<IslandMemberAnemia> islandMembersData) {
		this.islandMembersData = islandMembersData;
	}

	public List<IslandMemberAnemia> getIslandMembersAnemia() {
		return islandMembersData;
	}
}

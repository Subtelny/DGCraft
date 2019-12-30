package pl.subtelny.islands.repository.loader.island.member;

import java.util.List;

public class IslandMemberLoaderResult {

	private List<IslandMemberData> islandMembersData;

	public IslandMemberLoaderResult(List<IslandMemberData> islandMembersData) {
		this.islandMembersData = islandMembersData;
	}

	public List<IslandMemberData> getIslandMembersData() {
		return islandMembersData;
	}
}

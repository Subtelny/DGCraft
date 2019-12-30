package pl.subtelny.islands.repository.loader.island;

import java.util.List;

public class SkyblockIslandLoaderResult {

	private List<SkyblockIslandData> islandData;

	public SkyblockIslandLoaderResult(List<SkyblockIslandData> islandData) {
		this.islandData = islandData;
	}

	public List<SkyblockIslandData> getIslandData() {
		return islandData;
	}

}

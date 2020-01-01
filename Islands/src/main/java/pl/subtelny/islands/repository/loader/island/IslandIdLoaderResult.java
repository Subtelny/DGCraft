package pl.subtelny.islands.repository.loader.island;

import java.util.List;
import pl.subtelny.islands.model.island.IslandId;

public class IslandIdLoaderResult {

	private final List<IslandId> islandIds;

	public IslandIdLoaderResult(List<IslandId> islandIds) {
		this.islandIds = islandIds;
	}

	public List<IslandId> getIslandIds() {
		return islandIds;
	}
}

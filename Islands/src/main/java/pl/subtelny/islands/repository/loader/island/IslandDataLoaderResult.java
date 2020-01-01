package pl.subtelny.islands.repository.loader.island;

import java.util.List;

public class IslandDataLoaderResult {

	private final List<IslandData> loadedData;

	public IslandDataLoaderResult(List<IslandData> loadedData) {
		this.loadedData = loadedData;
	}

	public List<IslandData> getLoadedData() {
		return loadedData;
	}
}

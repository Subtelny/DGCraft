package pl.subtelny.islands.repository.loader.island;

import java.util.List;

public class IslandAnemiaLoaderResult {

	private final List<IslandAnemia> loadedData;

	public IslandAnemiaLoaderResult(List<IslandAnemia> loadedData) {
		this.loadedData = loadedData;
	}

	public List<IslandAnemia> getLoadedData() {
		return loadedData;
	}
}

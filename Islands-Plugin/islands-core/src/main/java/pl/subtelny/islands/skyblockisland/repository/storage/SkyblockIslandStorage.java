package pl.subtelny.islands.skyblockisland.repository.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.repository.Storage;

public class SkyblockIslandStorage extends Storage<IslandId, SkyblockIsland> {

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	private Cache<IslandCoordinates, SkyblockIslandId> islandCoordinatesCache;

	public SkyblockIslandStorage() {
		super(Caffeine.newBuilder().build());
		islandCoordinatesCache = Caffeine.newBuilder().build();
	}

	public Optional<SkyblockIslandId> getCache(IslandCoordinates islandCoordinates) {
		return Optional.ofNullable(islandCoordinatesCache.getIfPresent(islandCoordinates));
	}

	public void updateIslandCoordinates(SkyblockIsland skyblockIsland) {
		islandCoordinatesCache.put(skyblockIsland.getIslandCoordinates(), skyblockIsland.getIslandId());
	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return Optional.ofNullable(freeIslands.poll());
	}

	public boolean isIslandCoordinatesFree(IslandCoordinates islandCoordinates) {
		return freeIslands.contains(islandCoordinates);
	}

}

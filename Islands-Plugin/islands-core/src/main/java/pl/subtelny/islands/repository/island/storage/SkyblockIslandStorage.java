package pl.subtelny.islands.repository.island.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;

public class SkyblockIslandStorage {

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	private Cache<IslandCoordinates, Optional<IslandId>> islandCoordinatesCache;

	public SkyblockIslandStorage() {
		islandCoordinatesCache = Caffeine.newBuilder().build();
	}

	public Optional<IslandId> getCache(IslandCoordinates islandCoordinates, Function<IslandCoordinates, Optional<IslandId>> loadIslandId) {
		return islandCoordinatesCache.get(islandCoordinates, loadIslandId);
	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return Optional.ofNullable(freeIslands.poll());
	}

	public boolean isIslandCoordinatesFree(IslandCoordinates islandCoordinates) {
		return freeIslands.contains(islandCoordinates);
	}

}

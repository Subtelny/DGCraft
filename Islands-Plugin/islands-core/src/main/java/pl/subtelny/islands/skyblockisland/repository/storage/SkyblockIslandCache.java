package pl.subtelny.islands.skyblockisland.repository.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.freeislands.SimpleSeriesFreeIslandCoordinatesCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;

import java.util.Optional;
import java.util.Queue;

@Component
public class SkyblockIslandCache {

    private final Queue<IslandCoordinates> freeIslands;

    private final Cache<IslandId, IslandCoordinates> islandIdToCoordinatesCache;

    private final Cache<IslandCoordinates, IslandId> coordinatesToIslandIdCache;

    @Autowired
    public SkyblockIslandCache() {
        freeIslands = new SimpleSeriesFreeIslandCoordinatesCalculator().generateIslandCoordinates();
        coordinatesToIslandIdCache = Caffeine.newBuilder().build();
        islandIdToCoordinatesCache = Caffeine.newBuilder().build();
    }

    public void updateIslandCache(SkyblockIsland island) {
        islandIdToCoordinatesCache.put(island.getId(), island.getIslandCoordinates());
        coordinatesToIslandIdCache.put(island.getIslandCoordinates(), island.getId());
    }

    public Optional<IslandCoordinates> findIslandCoordinates(IslandId islandId) {
        return Optional.ofNullable(islandIdToCoordinatesCache.getIfPresent(islandId));
    }

    public void invalidateIsland(IslandId islandId) {
        findIslandCoordinates(islandId).ifPresent(coordinatesToIslandIdCache::invalidate);
        islandIdToCoordinatesCache.invalidate(islandId);
    }

    public Optional<IslandId> findIslandId(IslandCoordinates islandCoordinates) {
        return Optional.ofNullable(coordinatesToIslandIdCache.getIfPresent(islandCoordinates));
    }

    public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
        return Optional.ofNullable(freeIslands.poll());
    }

    public boolean isIslandCoordinatesFree(IslandCoordinates islandCoordinates) {
        return freeIslands.contains(islandCoordinates);
    }

    public void removeFreeIslandCoordinates(IslandCoordinates islandCoordinates) {
        freeIslands.remove(islandCoordinates);
    }
}

package pl.subtelny.islands.skyblockisland.repository.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.freeislands.SimpleSeriesFreeIslandCoordinatesCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.repository.Storage;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Queue;

public class SkyblockIslandStorage extends Storage<SkyblockIslandId, SkyblockIsland> {

    private Queue<IslandCoordinates> freeIslands;

    private Cache<IslandCoordinates, SkyblockIslandId> islandCoordinatesCache;

    public SkyblockIslandStorage() {
        super(Caffeine.newBuilder().build());
        freeIslands = new SimpleSeriesFreeIslandCoordinatesCalculator().generateIslandCoordinates();
        islandCoordinatesCache = Caffeine.newBuilder().build();
    }

    public Optional<SkyblockIslandId> getCache(IslandCoordinates islandCoordinates) {
        return Optional.ofNullable(islandCoordinatesCache.getIfPresent(islandCoordinates));
    }

    public void updateIslandCoordinates(@Nullable SkyblockIsland skyblockIsland) {
        if (skyblockIsland != null) {
            islandCoordinatesCache.put(skyblockIsland.getIslandCoordinates(), skyblockIsland.getIslandId());
        }
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

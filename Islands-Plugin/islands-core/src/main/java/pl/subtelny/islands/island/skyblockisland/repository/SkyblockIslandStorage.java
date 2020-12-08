package pl.subtelny.islands.island.skyblockisland.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.api.repository.Storage;
import pl.subtelny.islands.island.IslandCoordinates;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.island.repository.IslandStorage;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.utilities.NullObject;

import java.util.Optional;
import java.util.function.Function;

public class SkyblockIslandStorage extends IslandStorage<SkyblockIsland> {

    private final Storage<IslandCoordinates, NullObject<IslandId>> islandCoordinatesIslandIdCache;

    public SkyblockIslandStorage() {
        this.islandCoordinatesIslandIdCache = new Storage<>(Caffeine.newBuilder().build());
    }

    public void invalidate(SkyblockIsland skyblockIsland) {
        islandCoordinatesIslandIdCache.invalidate(skyblockIsland.getIslandCoordinates());
        invalidate(skyblockIsland.getId());
    }

    public void put(IslandCoordinates islandCoordinates, NullObject<IslandId> islandIdNullObject) {
        islandCoordinatesIslandIdCache.put(islandCoordinates, islandIdNullObject);
    }

    public NullObject<SkyblockIsland> getCache(IslandId islandId, Function<IslandId, NullObject<SkyblockIsland>> function) {
        Function<IslandId, NullObject<SkyblockIsland>> islandFunction = function.andThen(updateIslandCoordinatesFunction(islandId));
        return super.getCache(islandId, islandFunction);
    }

    public NullObject<SkyblockIsland> getCache(IslandCoordinates islandCoordinates,
                                               Function<IslandCoordinates, NullObject<SkyblockIsland>> function) {
        Function<IslandCoordinates, NullObject<IslandId>> islandFunction = function.andThen(updateIslandCoordinatesFunction());
        NullObject<IslandId> islandIdCache = islandCoordinatesIslandIdCache.getCache(islandCoordinates, islandFunction);
        return islandIdCache.get()
                .map(this::getCacheIfPresent)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseGet(NullObject::empty);
    }

    private Function<NullObject<SkyblockIsland>, NullObject<IslandId>> updateIslandCoordinatesFunction() {
        return skyblockIslandNullObject -> skyblockIslandNullObject.get()
                .map(AbstractIsland::getId)
                .map(NullObject::of)
                .orElseGet(NullObject::empty);
    }

    private Function<NullObject<SkyblockIsland>, NullObject<SkyblockIsland>> updateIslandCoordinatesFunction(IslandId islandId) {
        return skyblockIslandNullObject -> {
            skyblockIslandNullObject.get().ifPresent(skyblockIsland -> islandCoordinatesIslandIdCache.put(skyblockIsland.getIslandCoordinates(), NullObject.of(islandId)));
            return skyblockIslandNullObject;
        };
    }

}

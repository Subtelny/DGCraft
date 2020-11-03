package pl.subtelny.islands.island;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.query.IslandTypeCacheLoader;

@Component
public class IslandIdToIslandTypeService {

    private final LoadingCache<IslandId, IslandType> islandIdIslandTypeCache;

    public IslandIdToIslandTypeService(ConnectionProvider connectionProvider) {
        this.islandIdIslandTypeCache = Caffeine.newBuilder()
                .build(new IslandTypeCacheLoader(connectionProvider));
    }

    public IslandType getIslandType(IslandId islandId) {
        return islandIdIslandTypeCache.get(islandId);
    }

    public void update(Island island) {
        islandIdIslandTypeCache.put(island.getId(), island.getType());
    }

    public void invalidate(IslandId islandId) {
        islandIdIslandTypeCache.invalidate(islandId);
    }

}

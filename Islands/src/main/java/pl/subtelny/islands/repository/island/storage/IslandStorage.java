package pl.subtelny.islands.repository.island.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.repository.Storage;

@Component
public class IslandStorage extends Storage<IslandId, Optional<Island>> {

	private Cache<IslandId, IslandType> islandTypeCache;

	public IslandStorage() {
		super(Caffeine.newBuilder().build());
		islandTypeCache = Caffeine.newBuilder()
				.expireAfterAccess(1, TimeUnit.HOURS)
				.build();
	}

	public IslandType getIslandTypeCache(IslandId islandId, Function<IslandId, IslandType> function) {
		return islandTypeCache.get(islandId, function);
	}

}

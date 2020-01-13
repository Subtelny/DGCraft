package pl.subtelny.islands.repository.island.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.island.IslandAnemia;
import pl.subtelny.islands.repository.island.SkyblockIslandAnemia;
import pl.subtelny.islands.repository.island.loader.IslandLoadRequest;
import pl.subtelny.repository.Storage;

@Component
public class IslandStorage extends Storage<IslandId, Optional<Island>> {

	private Cache<IslandLoadRequest, Optional<IslandId>> requestCache;

	public IslandStorage() {
		super(Caffeine.newBuilder().build());
		requestCache = Caffeine.newBuilder().build();
	}

	public Optional<IslandId> getCache(IslandLoadRequest request,
			Function<? super IslandLoadRequest, ? extends Optional<IslandId>> requestFunction) {
		return requestCache.get(request, requestFunction);
	}



}

package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.loader.island.IslandIdLoader;
import pl.subtelny.islands.repository.loader.island.IslandIdLoaderRequest;

@Component
public class SkyblockIslandRepository {

	private final Configuration configuration;

	private final IslandRepository islandRepository;

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	private Cache<IslandCoordinates, Optional<IslandId>> islandCoordinatesCache;

	private Cache<AccountId, Optional<IslandId>> islanderCache;

	public SkyblockIslandRepository(IslandRepository islandRepository, DatabaseConfiguration databaseConfiguration) {
		this.configuration = databaseConfiguration.getConfiguration();
		this.islandRepository = islandRepository;
		this.islandCoordinatesCache = Caffeine.newBuilder().build();
		this.islanderCache = Caffeine.newBuilder().build();
	}

	public Optional<SkyblockIsland> findIslandByIslander(Islander islander) {
		AccountId accountId = islander.getAccount().getId();
		Optional<IslandId> islandId = islanderCache.get(accountId, this::findIslandIdByIslander);
		return findIslandByIslander(islandId);
	}

	private Optional<IslandId> findIslandIdByIslander(AccountId islander) {
		IslandIdLoaderRequest request = IslandIdLoaderRequest.newIslandMemberBuilder()
				.where(islander)
				.build();
		return performIslandIdLoader(request);
	}

	private Optional<IslandId> performIslandIdLoader(IslandIdLoaderRequest request) {
		IslandIdLoader loader = new IslandIdLoader(configuration, request);
		List<IslandId> islandIds = loader.perform().getIslandIds();
		if (islandIds.size() == 0) {
			return Optional.empty();
		}
		return Optional.of(islandIds.get(0));
	}

	private Optional<SkyblockIsland> findIslandByIslander(Optional<IslandId> islandId) {
		if (islandId.isEmpty()) {
			return Optional.empty();
		}
		Optional<Island> cacheOpt = islandRepository.getCache(islandId.get());
		if (cacheOpt.isEmpty()) {
			return Optional.empty();
		}
		Island island = cacheOpt.get();
		if (island.getIslandType() != IslandType.SKYBLOCK) {
			return Optional.empty();
		}
		return Optional.of((SkyblockIsland) island);
	}

	public Optional<SkyblockIsland> findIslandByIslander(IslandCoordinates islandCoordinates) {
		Optional<IslandId> islandId = islandCoordinatesCache.get(islandCoordinates, this::findIslandIdByIslandCoordinates);
		return findIslandByIslander(islandId);
	}

	private Optional<IslandId> findIslandIdByIslandCoordinates(IslandCoordinates islandCoordinates) {
		IslandIdLoaderRequest request = IslandIdLoaderRequest.newSkyblockBuilder()
				.where(islandCoordinates)
				.build();
		return performIslandIdLoader(request);
	}

	public void saveIsland(SkyblockIsland island) {

	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return Optional.ofNullable(freeIslands.poll());
	}

}

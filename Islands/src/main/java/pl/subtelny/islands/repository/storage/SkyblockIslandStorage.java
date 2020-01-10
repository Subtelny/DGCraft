package pl.subtelny.islands.repository.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.SkyblockIslandRepository;

@Component
public class SkyblockIslandStorage {

	private final IslandStorage islandStorage;

	private final SkyblockIslandRepository skyblockIslandRepository;

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	private Cache<IslandCoordinates, Optional<IslandId>> islandCoordinatesCache;

	private Cache<AccountId, Optional<IslandId>> islanderCache;

	@Autowired
	public SkyblockIslandStorage(SkyblockIslandRepository skyblockIslandRepository,
			IslandStorage islandStorage) {
		this.skyblockIslandRepository = skyblockIslandRepository;
		this.islandStorage = islandStorage;
		this.islandCoordinatesCache = Caffeine.newBuilder().build();
		this.islanderCache = Caffeine.newBuilder().build();
	}

	public Optional<SkyblockIsland> findSkyblockIsland(IslandId islandId) {
		Optional<Island> cache = islandStorage.getCache(islandId);
		if (cache.isPresent()) {
			Island island = cache.get();
			if (island.getIslandType() == IslandType.SKYBLOCK) {
				return Optional.of((SkyblockIsland) island);
			}
		}
		return Optional.empty();
	}

	public Optional<SkyblockIsland> findSkyblockIslandByIslander(Islander islander) {
		AccountId accountId = islander.getAccount().getAccountId();
		Optional<IslandId> islandIdOpt = islanderCache.get(accountId, skyblockIslandRepository::findIslandIdByIslander);
		if (islandIdOpt.isPresent()) {
			return findSkyblockIsland(islandIdOpt.get());
		}
		return Optional.empty();
	}

	public Optional<SkyblockIsland> findSkyblockIslandByCoordinates(IslandCoordinates islandCoordinates) {
		Optional<IslandId> islandIdOpt = islandCoordinatesCache.get(islandCoordinates, skyblockIslandRepository::findIslandIdByIslandCoordinates);
		if (islandIdOpt.isPresent()) {
			return findSkyblockIsland(islandIdOpt.get());
		}
		return Optional.empty();
	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return Optional.ofNullable(freeIslands.poll());
	}

}

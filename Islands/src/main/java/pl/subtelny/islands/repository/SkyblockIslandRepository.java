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

	public SkyblockIslandRepository(DatabaseConfiguration databaseConfiguration) {
		this.configuration = databaseConfiguration.getConfiguration();
	}

	public Optional<IslandId> findIslandIdByIslander(AccountId accountId) {
		IslandIdLoaderRequest request = IslandIdLoaderRequest.newIslandMemberBuilder()
				.where(accountId)
				.build();
		return performIslandIdLoader(request);
	}

	public Optional<IslandId> findIslandIdByIslandCoordinates(IslandCoordinates islandCoordinates) {
		IslandIdLoaderRequest request = IslandIdLoaderRequest.newSkyblockBuilder()
				.where(islandCoordinates)
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

	public void saveIsland(SkyblockIsland island) {

	}

}

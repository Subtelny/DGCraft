package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.loader.island.SkyblockIslandData;
import pl.subtelny.islands.repository.loader.island.SkyblockIslandLoader;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;

@Component
public class SkyblockIslandRepository {

	private Configuration configuration;

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	private Cache<IslandId, Optional<SkyblockIsland>> islandsCache;

	private Cache<IslandCoordinates, Optional<IslandId>> islandCoordinatesCache;

	private Cache<AccountId, Optional<IslandId>> islanderAccountIdCache;

	public SkyblockIslandRepository(DatabaseConfiguration databaseConfiguration) {
		super(Caffeine.newBuilder().build());
		this.islandCoordinatesCache = Caffeine.newBuilder().build();
		this.islanderAccountIdCache = Caffeine.newBuilder().build();
		this.islandsCache = Caffeine.newBuilder().build();
		this.configuration = databaseConfiguration.getConfiguration();
	}

	public Optional<SkyblockIsland> findIsland(IslandId islandId) {
		Optional<SkyblockIsland> islandOpt = islandsCache.get(islandId, this::findIsland);
		if (islandOpt.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(islandOpt.get());
	}

	private Optional<SkyblockIsland> findIslandByIslandId(IslandId islandId) {
		SkyblockIslandLoader loader = new SkyblockIslandLoader.Builder(configuration).where(islandId).build();
		List<SkyblockIslandData> result = loader.perform().getIslandData();
		if (result.size() == 0) {
			return Optional.empty();
		}
		SkyblockIslandData islandData = result.get(0);
		Cuboid cuboid = SkyblockIslandUtil.defaultCuboid(islandData.getIslandCoordinates());
		SkyblockIsland island = new SkyblockIsland(islandData.getIslandId(), islandData.getIslandCoordinates(), cuboid, islandData.getCreatedDate());
		return Optional.of(island);
	}

	public Optional<SkyblockIsland> findIsland(IslandCoordinates islandCoordinates) {
		Optional<IslandId> islandId = islandCoordinatesCache.get(islandCoordinates, this::findIslandIdByIslandCoordinates);
		if (islandId.isEmpty()) {
			return Optional.empty();
		}
		return getCache(islandId.get());
	}

	private Optional<IslandId> findIslandIdByIslandCoordinates(IslandCoordinates islandCoordinates) {
		Optional<Record1<Integer>> recordOne = DSL.using(this.configuration)
				.select(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID)
				.where(SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(islandCoordinates.getX())
						.and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(islandCoordinates.getZ())))
				.fetchOptional();
		return recordOne.map(integerRecord1 -> IslandId.of(integerRecord1.component1().longValue()));
	}

	public void saveIsland(SkyblockIsland island) {

	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return Optional.ofNullable(freeIslands.poll());
	}

}

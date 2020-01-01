package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.loader.island.IslandData;
import pl.subtelny.islands.repository.loader.island.IslandDataLoader;
import pl.subtelny.islands.repository.loader.island.IslandDataLoaderRequest;
import pl.subtelny.islands.repository.loader.island.SkyblockIslandData;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.repository.Storage;
import pl.subtelny.utils.cuboid.Cuboid;

@Component
public class IslandRepository extends Storage<IslandId, Optional<Island>> {

	private final Configuration configuration;

	private final IslanderRepository islanderRepository;

	public IslandRepository(DatabaseConfiguration databaseConfiguration,
			IslanderRepository islanderRepository) {
		super(Caffeine.newBuilder().build());
		this.configuration = databaseConfiguration.getConfiguration();
		this.islanderRepository = islanderRepository;
	}

	@Override
	public Function<? super IslandId, ? extends Optional<Island>> computeData() {
		return this::findIsland;
	}

	public Optional<Island> findIsland(IslandId islandId) {
		IslandDataLoaderRequest request = IslandDataLoaderRequest.newBuilder()
				.where(islandId)
				.build();

		IslandDataLoader loader = new IslandDataLoader(configuration, request);
		List<IslandData> loadedData = loader.perform().getLoadedData();

		return Optional.empty();
	}

	private class IslandMapper {

		private final IslandData islandData;

		private IslandMapper(IslandData islandData) {
			this.islandData = islandData;
		}

		public Island map() {
			IslandType type = islandData.getIslandType();
			Island island;

			if (type == IslandType.SKYBLOCK) {
				island = mapAsSkyblock();
			} else {

			}

		}

		public SkyblockIsland mapAsSkyblock() {
			SkyblockIslandData data = (SkyblockIslandData) islandData;
			AccountId owner = AccountId.of(data.getOwner());
			Optional<Islander> cacheOpt = islanderRepository.getCache(owner);

			Cuboid cuboid = SkyblockIslandUtil.defaultCuboid(data.getIslandCoordinates());
			return new SkyblockIsland(data.getIslandId(), data.getIslandCoordinates(), cuboid, data.getCreatedDate());
		}

	}
}

package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import javax.sql.DataSource;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.generated.tables.Islands;
import pl.subtelny.islands.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.repository.Storage;

@Component
public class SkyblockIslandRepository extends Storage<IslandId, SkyblockIsland> {

	private Configuration configuration;

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	private Map<IslandCoordinates, Optional<IslandId>> islandCoordinatesCache = new ConcurrentHashMap<>();

	public SkyblockIslandRepository(DatabaseConfiguration databaseConfiguration) {
		super(Caffeine.newBuilder().build());
		this.configuration = databaseConfiguration.getConfiguration();
	}

	@Override
	public Function<? super IslandId, ? extends SkyblockIsland> mappingFunction() {
		return null;
	}

	public Optional<SkyblockIsland> findIsland(Islander islander) {

		return Optional.empty();
	}

	public Optional<SkyblockIsland> findIsland(IslandCoordinates islandCoordinates) {
		Optional<IslandId> islandId;
		if (islandCoordinatesCache.containsKey(islandCoordinates)) {
			islandId = islandCoordinatesCache.get(islandCoordinates);
		} else {
		}
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

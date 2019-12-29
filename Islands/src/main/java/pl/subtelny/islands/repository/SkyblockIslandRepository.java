package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.generated.enums.Islandmembertype;
import pl.subtelny.islands.generated.enums.Islandtype;
import pl.subtelny.islands.generated.tables.IslandMembers;
import pl.subtelny.islands.generated.tables.Islands;
import pl.subtelny.islands.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.service.SkyblockIslandLoader;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.repository.Storage;
import pl.subtelny.utils.cuboid.Cuboid;

@Component
public class SkyblockIslandRepository extends Storage<IslandId, Optional<SkyblockIsland>> {

	private Configuration configuration;

	private Queue<IslandCoordinates> freeIslands = new ConcurrentLinkedQueue<>();

	private Cache<IslandCoordinates, Optional<IslandId>> islandCoordinatesCache;

	private Cache<AccountId, Optional<IslandId>> islanderAccountIdCache;

	public SkyblockIslandRepository(DatabaseConfiguration databaseConfiguration) {
		super(Caffeine.newBuilder().build());
		this.islandCoordinatesCache = Caffeine.newBuilder().build();
		this.islanderAccountIdCache = Caffeine.newBuilder().build();
		this.configuration = databaseConfiguration.getConfiguration();
	}

	@Override
	public Function<? super IslandId, ? extends Optional<SkyblockIsland>> mappingFunction() {
		return islandId -> {
			SkyblockIslandLoader loader = new SkyblockIslandLoader(this.configuration);
			loader.loadSkyblockIsland(islandId);


			Record record = recordOpt.get();
			int x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
			int z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
			Timestamp timestamp = record.get(Islands.ISLANDS.CREATED_DATE);
			LocalDate createdDate = timestamp.toLocalDateTime().toLocalDate();

			IslandCoordinates islandCoordinates = new IslandCoordinates(x,z);
			Cuboid cuboid = SkyblockIslandUtil.defaultCuboid(islandCoordinates);
			SkyblockIsland skyblockIsland = new SkyblockIsland(islandId, islandCoordinates, cuboid, createdDate);
			return Optional.of(skyblockIsland);
		};
	}

	public Optional<SkyblockIsland> findIsland(Islander islander) {
		AccountId accountId = islander.getAccount().getId();
		Optional<IslandId> islandId = islanderAccountIdCache.get(accountId, this::findIslandIdByAccountId);
		if(islandId.isEmpty()) {
			return Optional.empty();
		}
		return getCache(islandId.get());
	}

	private Optional<IslandId> findIslandIdByAccountId(AccountId accountId) {
		Condition member = IslandMembers.ISLAND_MEMBERS.ID.eq(accountId.toString());
		Condition islander = IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE.eq(Islandmembertype.ISLANDER);
		Condition type = Islands.ISLANDS.TYPE.eq(Islandtype.SKYBLOCK);

		Optional<Record1<Integer>> record = DSL.using(this.configuration)
				.select(IslandMembers.ISLAND_MEMBERS.ISLAND_ID)
				.from(IslandMembers.ISLAND_MEMBERS)
				.join(Islands.ISLANDS)
				.on(IslandMembers.ISLAND_MEMBERS.ISLAND_ID.eq(Islands.ISLANDS.ID))
				.where(member.and(islander).and(type))
				.fetchOptional();
		if (record.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(IslandId.of(record.get().value1().longValue()));
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

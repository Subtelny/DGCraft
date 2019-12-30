package pl.subtelny.islands.repository.loader.island;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import org.bukkit.Location;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import pl.subtelny.islands.generated.tables.Islands;
import pl.subtelny.islands.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.loader.Loader;
import pl.subtelny.islands.utils.LocationSerializer;

public class SkyblockIslandLoader extends Loader<SkyblockIslandLoaderRequest, SkyblockIslandLoaderResult> {

	private final Configuration configuration;

	private final List<Condition> where;

	private SkyblockIslandLoader(Configuration configuration, List<Condition> where) {
		this.configuration = configuration;
		this.where = where;
	}

	@Override
	public SkyblockIslandLoaderResult perform() {
		List<SkyblockIslandData> islandsData = loadFromDbIslandRecord();
		return new SkyblockIslandLoaderResult(islandsData);
	}

	private List<SkyblockIslandData> loadFromDbIslandRecord() {
		return DSL.using(this.configuration)
				.select()
				.from(Islands.ISLANDS)
				.join(SkyblockIslands.SKYBLOCK_ISLANDS)
				.on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID))
				.where(where)
				.fetch(record -> new SkyblockIslandDataMapper().map(record));
	}

	private static class SkyblockIslandDataMapper implements RecordMapper<Record, SkyblockIslandData> {

		@Override
		public SkyblockIslandData map(Record record) {
			int owner = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.OWNER);
			int x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
			int z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
			IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);
			LocalDate createdDate = record.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime().toLocalDate();
			Location spawn = LocationSerializer.deserializeMinimalistic(record.get(Islands.ISLANDS.SPAWN));
			IslandId islandId = IslandId.of(record.get(Islands.ISLANDS.ID).longValue());
			return new SkyblockIslandData(islandId, islandCoordinates, createdDate, owner, spawn);
		}
	}

	public static class Builder {

		private List<Condition> where = Lists.newArrayList();

		private final Configuration configuration;

		public Builder(Configuration configuration) {
			this.configuration = configuration;
		}

		public Builder where(IslandCoordinates islandCoordinates) {
			Condition islandCoords = SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(islandCoordinates.getX())
					.and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(islandCoordinates.getZ()));
			where.add(islandCoords);
			return this;
		}

		public Builder where(IslandId islandId) {
			Condition islandIdentity = SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID.eq(islandId.getId().intValue());
			where.add(islandIdentity);
			return this;
		}

		public SkyblockIslandLoader build() {
			return new SkyblockIslandLoader(configuration, where);
		}

	}

}

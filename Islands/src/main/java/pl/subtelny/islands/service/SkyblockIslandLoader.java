package pl.subtelny.islands.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import pl.subtelny.islands.generated.tables.IslandMembers;
import pl.subtelny.islands.generated.tables.Islands;
import pl.subtelny.islands.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.utils.LocationSerializer;

public class SkyblockIslandLoader {

	private final Configuration configuration;

	public SkyblockIslandLoader(Configuration configuration) {
		this.configuration = configuration;
	}

	public Optional<SkyblockIslandRecordResult> loadSkyblockIsland(IslandId islandId) {
		Optional<Record> islandOpt = loadFromDbIslandRecord(islandId);
		if (islandOpt.isEmpty()) {
			return Optional.empty();
		}

		Record islandRecord = islandOpt.get();
		int x = islandRecord.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
		int z = islandRecord.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
		LocalDate createdDate = islandRecord.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime().toLocalDate();
		Location spawn = LocationSerializer.deserializeMinimalistic(islandRecord.get(Islands.ISLANDS.SPAWN));
		IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);

		List<IslandMemberResult> islandMembers = new ArrayList<>();
		loadFromDbIslandMembers(isla)

		SkyblockIslandRecordResult result = new SkyblockIslandRecordResult(islandCoordinates, createdDate, spawn, islandMembers);
		return Optional.of(result);
	}

	private Result<Record> loadFromDbIslandMembers(IslandId islandId) {
		return DSL.using(this.configuration)
				.select()
				.from(IslandMembers.ISLAND_MEMBERS)
				.where(IslandMembers.ISLAND_MEMBERS.ISLAND_ID.eq(islandId.getId().intValue()))
				.fetch();
	}

	private Optional<Record> loadFromDbIslandRecord(IslandId islandId) {
		return DSL.using(this.configuration)
				.select()
				.from(Islands.ISLANDS)
				.join(SkyblockIslands.SKYBLOCK_ISLANDS)
				.on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID))
				.where(Islands.ISLANDS.ID.eq(islandId.getId().intValue()))
				.fetchOptional();
	}

	private class SkyblockIslandRecordResult {

		private final IslandCoordinates islandCoordinates;

		private final LocalDate createdDate;

		private final Location spawn;

		private final List<IslandMemberResult> islandMembers;

		private SkyblockIslandRecordResult(IslandCoordinates islandCoordinates,
				LocalDate createdDate,
				Location spawn,
				List<IslandMemberResult> islandMembers) {
			this.islandCoordinates = islandCoordinates;
			this.createdDate = createdDate;
			this.spawn = spawn;
			this.islandMembers = islandMembers;
		}
	}

	public class IslandMemberResult {

		private final String id;

		private final IslandMemberType islandMemberType;

		public IslandMemberResult(String id, IslandMemberType islandMemberType) {
			this.id = id;
			this.islandMemberType = islandMemberType;
		}
	}
}

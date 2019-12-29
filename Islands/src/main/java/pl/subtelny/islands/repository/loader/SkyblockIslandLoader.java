package pl.subtelny.islands.repository.loader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import pl.subtelny.islands.generated.enums.Islandmembertype;
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
		int owner = islandRecord.get(SkyblockIslands.SKYBLOCK_ISLANDS.OWNER);
		int x = islandRecord.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
		int z = islandRecord.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
		LocalDate createdDate = islandRecord.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime().toLocalDate();
		Location spawn = LocationSerializer.deserializeMinimalistic(islandRecord.get(Islands.ISLANDS.SPAWN));
		IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);

		List<IslandMemberResult> islandMembers = new ArrayList<>();
		Result<Record> membersRecord = loadFromDbIslandMembers(islandId);
		membersRecord.forEach(record -> {
			String id = record.get(IslandMembers.ISLAND_MEMBERS.ID);
			Islandmembertype islandmembertype = record.get(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE);
			IslandMemberType islandMemberType = IslandMemberType.valueOf(islandmembertype.getLiteral());
			IslandMemberResult result = new IslandMemberResult(id, islandMemberType);
			islandMembers.add(result);
		});

		SkyblockIslandRecordResult result = new SkyblockIslandRecordResult(islandCoordinates, createdDate, owner, spawn, islandMembers);
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

}

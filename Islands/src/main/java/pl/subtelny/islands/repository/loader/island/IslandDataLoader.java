package pl.subtelny.islands.repository.loader.island;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.islands.generated.tables.GuildIslands;
import pl.subtelny.islands.generated.tables.Islands;
import pl.subtelny.islands.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.loader.Loader;
import pl.subtelny.islands.utils.LocationSerializer;
import pl.subtelny.utils.CuboidUtil;
import pl.subtelny.utils.cuboid.Cuboid;

public class IslandDataLoader extends Loader<IslandDataLoaderResult> {

	private final Configuration configuration;

	private final IslandDataLoaderRequest request;

	public IslandDataLoader(Configuration configuration, IslandDataLoaderRequest request) {
		this.configuration = configuration;
		this.request = request;
	}

	@Override
	public IslandDataLoaderResult perform() {
		List<IslandData> islandData = loadIslandData();
		return new IslandDataLoaderResult(islandData);
	}

	private List<IslandData> loadIslandData() {
		return DSL.using(this.configuration)
				.select()
				.from(Islands.ISLANDS)

				.leftOuterJoin(SkyblockIslands.SKYBLOCK_ISLANDS)
				.on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID))

				.leftOuterJoin(GuildIslands.GUILD_ISLANDS)
				.on(Islands.ISLANDS.ID.eq(GuildIslands.GUILD_ISLANDS.ISLAND_ID))

				.where(request.getWhere())
				.fetch(this::computeIslandData);
	}

	private IslandData computeIslandData(Record record) {
		IslandType type = IslandType.valueOf(record.get(Islands.ISLANDS.TYPE).getLiteral());
		IslandData islandData = mapIslandData(record, type);
		if (type == IslandType.SKYBLOCK) {
			return mapSkyblockIslandData(islandData, record);
		}
		return mapGuildIslandData(islandData, record);
	}

	private IslandData mapIslandData(Record record, IslandType type) {
		LocalDate createdDate = record.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime().toLocalDate();
		Location spawn = LocationSerializer.deserializeMinimalistic(record.get(Islands.ISLANDS.SPAWN));
		IslandId islandId = IslandId.of(record.get(Islands.ISLANDS.ID).longValue());
		return new IslandData(islandId, type, createdDate, spawn);
	}

	private SkyblockIslandData mapSkyblockIslandData(IslandData islandData, Record record) {
		UUID owner = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.OWNER);
		int x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
		int z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
		IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);
		return new SkyblockIslandData(islandCoordinates, owner, islandData);
	}

	private GuildIslandData mapGuildIslandData(IslandData islandData, Record record) {
		GuildIslands guildIslands = GuildIslands.GUILD_ISLANDS;
		int owner = record.get(guildIslands.OWNER);
		LocalDateTime protection = record.get(guildIslands.PROTECTION).toLocalDateTime();
		Cuboid cuboid = CuboidUtil.deserialize(record.get(guildIslands.CUBOID));
		return new GuildIslandData(owner, protection, islandData, cuboid);
	}

}

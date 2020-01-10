package pl.subtelny.islands.repository.loader.island;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.generated.tables.GuildIslands;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.repository.Loader;
import pl.subtelny.islands.utils.LocationSerializer;
import pl.subtelny.repository.LoaderResult;
import pl.subtelny.utils.CuboidUtil;
import pl.subtelny.utils.cuboid.Cuboid;

public class IslandAnemiaLoader extends Loader<IslandAnemia> {

	private final Configuration configuration;

	private final IslandAnemiaLoaderRequest request;

	public IslandAnemiaLoader(Configuration configuration, IslandAnemiaLoaderRequest request) {
		this.configuration = configuration;
		this.request = request;
	}

	@Override
	public LoaderResult<IslandAnemia> perform() {
		List<IslandAnemia> islandData = loadIslandAnemia();
		return new LoaderResult<>(islandData);
	}

	private List<IslandAnemia> loadIslandAnemia() {
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

	private IslandAnemia computeIslandData(Record record) {
		IslandType type = IslandType.valueOf(record.get(Islands.ISLANDS.TYPE).getLiteral());
		IslandAnemia islandData = mapIslandData(record, type);
		if (type == IslandType.SKYBLOCK) {
			return mapSkyblockIslandData(islandData, record);
		}
		return mapGuildIslandData(islandData, record);
	}

	private IslandAnemia mapIslandData(Record record, IslandType type) {
		LocalDateTime createdDate = record.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime();
		Location spawn = LocationSerializer.deserializeMinimalistic(record.get(Islands.ISLANDS.SPAWN));
		IslandId islandId = IslandId.of(record.get(Islands.ISLANDS.ID).longValue());
		return new IslandAnemia(islandId, type, createdDate, spawn);
	}

	private SkyblockIslandAnemia mapSkyblockIslandData(IslandAnemia islandAnemia, Record record) {
		UUID owner = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.OWNER);
		AccountId ownerId = AccountId.of(owner);
		int x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
		int z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
		IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);
		int extendLevel = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.EXTEND_LEVEL);
		return new SkyblockIslandAnemia(islandAnemia, islandCoordinates, ownerId, extendLevel);
	}

	private GuildIslandAnemia mapGuildIslandData(IslandAnemia islandAnemia, Record record) {
		GuildIslands guildIslands = GuildIslands.GUILD_ISLANDS;
		int owner = record.get(guildIslands.OWNER);
		GuildId ownerId = GuildId.of(owner);
		LocalDateTime protection = record.get(guildIslands.PROTECTION).toLocalDateTime();
		Cuboid cuboid = CuboidUtil.deserialize(record.get(guildIslands.CUBOID));
		return new GuildIslandAnemia(islandAnemia, ownerId, protection, cuboid);
	}

}

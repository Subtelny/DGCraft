package pl.subtelny.islands.repository.island.loader;

import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Location;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import pl.subtelny.core.generated.enums.Islandtype;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.island.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.utils.LocationSerializer;

public class SkyblockIslandAnemiaLoadAction extends IslandAnemiaLoadAction<SkyblockIslandAnemia> {

	private final SkyblockIslandLoadRequest request;

	public SkyblockIslandAnemiaLoadAction(Configuration configuration, SkyblockIslandLoadRequest request) {
		super(configuration);
		this.request = request;
	}

	@Override
	protected List<Condition> whereConditions() {
		Optional<IslandCoordinates> islandCoordinatesOpt = request.getIslandCoordinates();
		Optional<IslandId> islandIdOpt = request.getIslandId();

		List<Condition> conditions = Lists.newArrayList();
		if (islandCoordinatesOpt.isPresent()) {
			IslandCoordinates islandCoordinates = islandCoordinatesOpt.get();
			Condition islandCoords = SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(islandCoordinates.getX())
					.and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(islandCoordinates.getZ()));
			conditions.add(islandCoords);
		}
		if (islandIdOpt.isPresent()) {
			IslandId islandId = islandIdOpt.get();
			Condition islandIdentity = SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID.eq(islandId.getId().intValue());
			conditions.add(islandIdentity);
		}
		Condition skyblockType = Islands.ISLANDS.TYPE.eq(Islandtype.SKYBLOCK);
		conditions.add(skyblockType);
		return conditions;
	}

	@Override
	protected SkyblockIslandAnemia mapRecordIntoAnemia(Record record) {
		SkyblockIslandAnemiaDTO anemiaDTO = mapRecordToVariables(record);
		IslandId islandId = anemiaDTO.islandId;
		LocalDateTime createdDate = anemiaDTO.createdDate;
		Location spawn = anemiaDTO.spawn;
		IslandCoordinates islandCoordinates = anemiaDTO.islandCoordinates;
		AccountId ownerId = anemiaDTO.accountId;
		int extendLevel = anemiaDTO.extendLevel;
		return new SkyblockIslandAnemia(islandId, createdDate, spawn, islandCoordinates, ownerId, extendLevel);
	}

	private SkyblockIslandAnemiaDTO mapRecordToVariables(Record record) {
		LocalDateTime createdDate = record.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime();
		Location spawn = LocationSerializer.deserializeMinimalistic(record.get(Islands.ISLANDS.SPAWN));
		IslandId islandId = IslandId.of(record.get(Islands.ISLANDS.ID).longValue());
		UUID owner = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.OWNER);
		AccountId ownerId = AccountId.of(owner);
		int x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
		int z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
		IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);
		int extendLevel = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.EXTEND_LEVEL);
		return new SkyblockIslandAnemiaDTO(
				islandId,
				createdDate,
				spawn,
				islandCoordinates,
				ownerId,
				extendLevel);
	}

	@Override
	protected IslandType getIslandType() {
		return IslandType.SKYBLOCK;
	}

	private static class SkyblockIslandAnemiaDTO {

		public LocalDateTime createdDate;

		public Location spawn;

		public IslandId islandId;

		public AccountId accountId;

		public IslandCoordinates islandCoordinates;

		public int extendLevel;

		SkyblockIslandAnemiaDTO(
				IslandId islandId,
				LocalDateTime createdDate,
				Location spawn,
				IslandCoordinates islandCoordinates,
				AccountId accountId,
				int extendLevel) {
			this.createdDate = createdDate;
			this.spawn = spawn;
			this.islandId = islandId;
			this.accountId = accountId;
			this.islandCoordinates = islandCoordinates;
			this.extendLevel = extendLevel;
		}

	}

}

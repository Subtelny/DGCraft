package pl.subtelny.islands.repository.island.loader;

import com.google.common.collect.Lists;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.repository.island.anemia.SkyblockIslandAnemia;
import org.bukkit.Location;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import pl.subtelny.generated.tables.enums.Islandtype;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.utilities.location.LocationSerializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SkyblockIslandAnemiaLoadAction extends IslandAnemiaLoadAction<SkyblockIslandAnemia> {

    private final SkyblockIslandLoadRequest request;

    public SkyblockIslandAnemiaLoadAction(Configuration configuration, SkyblockIslandLoadRequest request) {
        super(configuration);
        this.request = request;
    }

    @Override
    protected List<Condition> whereConditions() {
        List<Condition> conditions = Lists.newArrayList();

		Optional<IslandCoordinates> islandCoordinatesOpt = request.getIslandCoordinates();
        islandCoordinatesOpt.ifPresent(islandCoordinates -> conditions.add(SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(islandCoordinates.getX())
                .and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(islandCoordinates.getZ()))));

		Optional<IslandId> islandIdOpt = request.getIslandId();
        islandIdOpt.ifPresent(islandId -> conditions.add(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID.eq(islandId.getId())));

        conditions.add(Islands.ISLANDS.TYPE.eq(Islandtype.SKYBLOCK));
        return conditions;
    }

    @Override
    protected SkyblockIslandAnemia mapRecordIntoAnemia(Record record) {
        return mapRecordToAnemia(record);
    }

    private SkyblockIslandAnemia mapRecordToAnemia(Record record) {
        LocalDateTime createdDate = record.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime();
        Location spawn = LocationSerializer.deserializeMinimalistic(record.get(Islands.ISLANDS.SPAWN));
        IslandId islandId = IslandId.of(record.get(Islands.ISLANDS.ID));
        UUID owner = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.OWNER);
        AccountId ownerId = AccountId.of(owner);
        int x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
        int z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
        IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);
        int extendLevel = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.EXTEND_LEVEL);
        int points = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.POINTS);
        return new SkyblockIslandAnemia(
                islandId,
                createdDate,
                spawn,
                islandCoordinates,
                ownerId,
                extendLevel,
                points);
    }

    @Override
    protected IslandType getIslandType() {
        return IslandType.SKYBLOCK;
    }

}

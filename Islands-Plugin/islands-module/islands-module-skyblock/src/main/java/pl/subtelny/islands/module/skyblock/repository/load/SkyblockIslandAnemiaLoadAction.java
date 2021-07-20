package pl.subtelny.islands.module.skyblock.repository.load;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.jooq.Record;
import org.jooq.*;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.repository.IslandAnemiaLoadAction;
import pl.subtelny.islands.api.repository.anemia.IslandAnemia;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.module.skyblock.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.utilities.location.LocationSerializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SkyblockIslandAnemiaLoadAction extends IslandAnemiaLoadAction<SkyblockIslandAnemia> {

    private final SkyblockIslandLoadRequest request;

    public SkyblockIslandAnemiaLoadAction(DSLContext connection, SkyblockIslandLoadRequest request) {
        super(connection);
        this.request = request;
    }

    @Override
    protected List<Condition> whereConditions() {
        List<Condition> conditions = Lists.newArrayList();

        Optional<IslandCoordinates> islandCoordinatesOpt = request.getIslandCoordinates();
        islandCoordinatesOpt.ifPresent(islandCoordinates ->
                conditions.add(SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(islandCoordinates.getX())
                        .and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(islandCoordinates.getZ()))));

        Optional<IslandId> islandIdOpt = request.getIslandId();
        islandIdOpt.ifPresent(islandId -> conditions.add(Islands.ISLANDS.ID.eq(islandId.getId())));
        return conditions;
    }

    @Override
    protected SkyblockIslandAnemia mapRecordIntoAnemia(Record record) {
        return mapRecordToAnemia(record);
    }

    @Override
    protected SelectOnConditionStep<Record> addJoinIslandToQuery(SelectJoinStep<Record> from) {
        return from
                .leftOuterJoin(SkyblockIslands.SKYBLOCK_ISLANDS)
                .on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID));
    }

    private SkyblockIslandAnemia mapRecordToAnemia(Record record) {
        LocalDateTime createdDate = record.get(Islands.ISLANDS.CREATED_DATE).toLocalDateTime();
        Location spawn = LocationSerializer.deserializeMinimalistic(record.get(Islands.ISLANDS.SPAWN));
        IslandType islandType = IslandType.of(record.get(Islands.ISLANDS.TYPE));
        Integer id = record.get(Islands.ISLANDS.ID);
        IslandId islandId = IslandId.of(id, islandType);
        int points = record.get(Islands.ISLANDS.POINTS);
        int x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
        int z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
        IslandCoordinates islandCoordinates = new IslandCoordinates(x, z);
        int extendLevel = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.EXTEND_LEVEL);

        IslandAnemia islandAnemia = new IslandAnemia(islandId, islandType, createdDate, spawn, points);
        return new SkyblockIslandAnemia(islandAnemia, islandCoordinates, extendLevel);
    }

}

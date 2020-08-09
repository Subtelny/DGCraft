package pl.subtelny.islands.skyblockisland.repository.loader;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.jooq.*;
import pl.subtelny.generated.tables.enums.Islandtype;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.island.repository.loader.IslandAnemiaLoadAction;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
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
        islandCoordinatesOpt.ifPresent(islandCoordinates ->
                conditions.add(SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(islandCoordinates.getX())
                        .and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(islandCoordinates.getZ()))));

        Optional<IslandId> islandIdOpt = request.getIslandId();
        islandIdOpt.ifPresent(islandId -> conditions.add(Islands.ISLANDS.ID.eq(islandId.getId())));

        conditions.add(Islands.ISLANDS.TYPE.eq(Islandtype.SKYBLOCK));
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
        SkyblockIslandId islandId = new SkyblockIslandId(record.get(Islands.ISLANDS.ID));
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
                extendLevel,
                points);
    }

}

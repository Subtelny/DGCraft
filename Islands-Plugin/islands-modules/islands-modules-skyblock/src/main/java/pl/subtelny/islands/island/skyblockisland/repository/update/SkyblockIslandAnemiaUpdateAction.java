package pl.subtelny.islands.island.skyblockisland.repository.update;

import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.Record1;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.generated.tables.tables.records.SkyblockIslandsRecord;
import pl.subtelny.islands.island.skyblockisland.IslandCoordinates;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.repository.IslandAnemiaUpdateAction;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;

public class SkyblockIslandAnemiaUpdateAction extends IslandAnemiaUpdateAction<SkyblockIslandAnemia, IslandId> {

    public SkyblockIslandAnemiaUpdateAction(DSLContext connection) {
        super(connection);
    }

    @Override
    public IslandId perform(SkyblockIslandAnemia islandAnemia) {
        IslandId islandId = islandAnemia.getIslandId();
        if (islandId == null) {
            return executeMissingId(islandAnemia);
        }
        connection.batch(
                getIslandRecordStatement(islandAnemia),
                getSkyblockIslandRecordStatement(islandAnemia)
        ).execute();
        return islandId;
    }

    private IslandId executeMissingId(SkyblockIslandAnemia islandAnemia) {
        Record1<Integer> record = getIslandRecordStatement(islandAnemia)
                .returningResult(Islands.ISLANDS.ID).fetchOne();
        IslandId id = IslandId.of(record.get(Islands.ISLANDS.ID), islandAnemia.getIslandType());
        islandAnemia.setIslandId(id);
        getSkyblockIslandRecordStatement(islandAnemia).execute();
        return id;
    }

    private InsertOnDuplicateSetMoreStep<SkyblockIslandsRecord> getSkyblockIslandRecordStatement(SkyblockIslandAnemia islandAnemia) {
        SkyblockIslandsRecord record = createRecord(islandAnemia);
        return connection
                .insertInto(SkyblockIslands.SKYBLOCK_ISLANDS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private SkyblockIslandsRecord createRecord(SkyblockIslandAnemia islandAnemia) {
        SkyblockIslandsRecord record = connection.newRecord(SkyblockIslands.SKYBLOCK_ISLANDS);
        record.setIslandId(islandAnemia.getIslandId().getId());
        record.setExtendLevel(islandAnemia.getExtendLevel());
        IslandCoordinates islandCoordinates = islandAnemia.getIslandCoordinates();
        record.setX(islandCoordinates.getX());
        record.setZ(islandCoordinates.getZ());
        return record;
    }
}

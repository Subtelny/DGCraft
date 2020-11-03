package pl.subtelny.islands.island.repository;

import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.records.IslandsRecord;
import pl.subtelny.islands.island.repository.anemia.IslandAnemia;
import pl.subtelny.utilities.location.LocationSerializer;
import pl.subtelny.core.api.repository.UpdateAction;

import java.sql.Timestamp;

public abstract class IslandAnemiaUpdateAction<T extends IslandAnemia, R> implements UpdateAction<T, R> {

    protected final DSLContext connection;

    public IslandAnemiaUpdateAction(DSLContext connection) {
        this.connection = connection;
    }

    protected InsertOnDuplicateSetMoreStep<IslandsRecord> getIslandRecordStatement(IslandAnemia islandAnemia) {
        IslandsRecord record = createIslandsRecord(islandAnemia);
        return connection
                .insertInto(Islands.ISLANDS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private IslandsRecord createIslandsRecord(IslandAnemia islandAnemia) {
        IslandsRecord islandsRecord = connection.newRecord(Islands.ISLANDS);
        Timestamp createdDate = Timestamp.valueOf(islandAnemia.getCreatedDate());
        islandsRecord.setCreatedDate(createdDate);
        islandsRecord.setType(islandAnemia.getType());

        String serializedSpawn = LocationSerializer.serializeMinimalistic(islandAnemia.getSpawn());
        islandsRecord.setSpawn(serializedSpawn);
        return islandsRecord;
    }
}

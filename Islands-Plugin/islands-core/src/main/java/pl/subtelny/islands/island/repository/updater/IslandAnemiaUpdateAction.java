package pl.subtelny.islands.island.repository.updater;

import org.jooq.InsertOnDuplicateSetMoreStep;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.records.IslandsRecord;
import pl.subtelny.islands.island.repository.anemia.IslandAnemia;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.utilities.location.LocationSerializer;
import pl.subtelny.repository.UpdateAction;

import java.sql.Timestamp;

public abstract class IslandAnemiaUpdateAction<T extends IslandAnemia, R> implements UpdateAction<T, R> {

    protected final Configuration configuration;

    public IslandAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    protected InsertOnDuplicateSetMoreStep<IslandsRecord> getIslandRecordStatement(IslandAnemia islandAnemia) {
        IslandsRecord record = createIslandsRecord(islandAnemia);
        return DSL.using(configuration)
                .insertInto(Islands.ISLANDS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private IslandsRecord createIslandsRecord(IslandAnemia islandAnemia) {
        IslandsRecord islandsRecord = DSL.using(configuration).newRecord(Islands.ISLANDS);
        Timestamp createdDate = Timestamp.valueOf(islandAnemia.getCreatedDate());
        islandsRecord.setCreatedDate(createdDate);

        String serializedSpawn = LocationSerializer.serializeMinimalistic(islandAnemia.getSpawn());
        islandsRecord.setSpawn(serializedSpawn);
        return islandsRecord;
    }
}

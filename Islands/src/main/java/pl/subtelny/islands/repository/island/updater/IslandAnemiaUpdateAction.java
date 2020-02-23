package pl.subtelny.islands.repository.island.updater;

import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.enums.Islandtype;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.core.generated.tables.records.IslandsRecord;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.utils.LocationSerializer;
import pl.subtelny.repository.UpdateAction;

import java.sql.Timestamp;

public abstract class IslandAnemiaUpdateAction<T extends IslandAnemia> implements UpdateAction<T> {

    protected final Configuration configuration;

    public IslandAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void perform(T islandAnemia) {
        saveIslandAnemia(islandAnemia);
        saveBasedIslandAnemia(islandAnemia);
    }

    public abstract void saveBasedIslandAnemia(T islandAnemia);

    private void saveIslandAnemia(IslandAnemia islandAnemia) {
        IslandsRecord islandsRecord = DSL.using(configuration).newRecord(Islands.ISLANDS);
        Timestamp createdDate = Timestamp.valueOf(islandAnemia.getCreatedDate());
        islandsRecord.setCreatedDate(createdDate);

        String serializedSpawn = LocationSerializer.serializeMinimalistic(islandAnemia.getSpawn());
        islandsRecord.setSpawn(serializedSpawn);

        Islandtype islandType = Islandtype.valueOf(islandAnemia.getIslandType().name());
        islandsRecord.setType(islandType);
        islandsRecord.store();
    }
}

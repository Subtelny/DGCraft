package pl.subtelny.islands.island.repository;

import com.google.gson.Gson;
import org.jooq.DSLContext;
import org.jooq.JSON;
import pl.subtelny.core.api.repository.UpdateAction;
import pl.subtelny.generated.tables.tables.IslandConfigurations;
import pl.subtelny.generated.tables.tables.records.IslandConfigurationsRecord;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.utilities.configuration.Configuration;

import java.util.Map;

public class IslandConfigurationUpdateAction implements UpdateAction<Island, Void> {

    private final DSLContext connection;

    public IslandConfigurationUpdateAction(DSLContext connection) {
        this.connection = connection;
    }

    @Override
    public Void perform(Island island) {
        Configuration configuration = island.getConfiguration().getConfiguration();
        Map<String, String> values = configuration.asMap();

        if (values.isEmpty()) {
            removeConfiguration(island.getId());
        } else {
            String rawJson = new Gson().toJson(values);
            updateConfiguration(island.getId(), rawJson);
        }
        return null;
    }

    private void updateConfiguration(IslandId islandId, String rawJson) {
        IslandConfigurationsRecord record = toRecord(islandId, rawJson);
        connection.insertInto(IslandConfigurations.ISLAND_CONFIGURATIONS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record)
                .execute();
    }

    private void removeConfiguration(IslandId id) {
        connection.deleteFrom(IslandConfigurations.ISLAND_CONFIGURATIONS)
                .where(IslandConfigurations.ISLAND_CONFIGURATIONS.ISLAND_ID.eq(id.getId()))
                .execute();
    }

    private IslandConfigurationsRecord toRecord(IslandId islandId, String rawJson) {
        IslandConfigurationsRecord record = connection.newRecord(IslandConfigurations.ISLAND_CONFIGURATIONS);
        record.setIslandId(islandId.getId());
        record.setConfiguration(JSON.valueOf(rawJson));
        return record;
    }

}

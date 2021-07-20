package pl.subtelny.islands.api.repository;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.jooq.JSON;
import org.jooq.Record;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.repository.HeavyRepository;
import pl.subtelny.generated.tables.tables.IslandConfigurations;
import pl.subtelny.generated.tables.tables.records.IslandConfigurationsRecord;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandConfiguration;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.utilities.configuration.Configuration;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

import static pl.subtelny.generated.tables.tables.IslandConfigurations.ISLAND_CONFIGURATIONS;

@Component
public class IslandConfigurationRepository extends HeavyRepository {

    @Autowired
    public IslandConfigurationRepository(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    public IslandConfiguration loadConfiguration(IslandId islandId) {
        Map<String, String> fields = session(context -> {
            return context.select(ISLAND_CONFIGURATIONS.CONFIGURATION)
                    .from(ISLAND_CONFIGURATIONS)
                    .where(ISLAND_CONFIGURATIONS.ISLAND_ID.eq(islandId.getId()))
                    .fetchOne(this::getRawConfiguration);
        });
        return Optional.ofNullable(fields)
                .map(stringStringMap -> new IslandConfiguration(fields))
                .orElseGet(IslandConfiguration::new);
    }

    public void saveConfiguration(Island island) {
        Configuration configuration = island.getConfiguration();
        Map<String, String> values = configuration.asMap();
        if (values.isEmpty()) {
            removeConfiguration(island.getId());
        } else {
            String rawJson = new Gson().toJson(values);
            updateConfiguration(island.getId(), rawJson);
        }
    }

    public void removeConfiguration(IslandId islandId) {
        Integer id = islandId.getId();
        session(context -> {
            context.deleteFrom(ISLAND_CONFIGURATIONS).where(ISLAND_CONFIGURATIONS.ISLAND_ID.eq(id));
        });
    }

    private void updateConfiguration(IslandId islandId, String rawJson) {
        session(context -> {
            JSON value = JSON.valueOf(rawJson);
            context.insertInto(ISLAND_CONFIGURATIONS, ISLAND_CONFIGURATIONS.ISLAND_ID, ISLAND_CONFIGURATIONS.CONFIGURATION)
                    .values(islandId.getId(), value)
                    .onDuplicateKeyUpdate()
                    .set(ISLAND_CONFIGURATIONS.CONFIGURATION, value)
                    .execute();
        });
    }

    private Map<String, String> getRawConfiguration(Record record) {
        String rawJson = record.get(ISLAND_CONFIGURATIONS.CONFIGURATION).data();
        Gson gson = new Gson();
        Type configurationMap = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(rawJson, configurationMap);
    }

}

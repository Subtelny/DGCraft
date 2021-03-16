package pl.subtelny.islands.island.repository;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.jooq.DSLContext;
import org.jooq.Record;
import pl.subtelny.core.api.repository.LoadAction;
import pl.subtelny.generated.tables.tables.IslandConfigurations;
import pl.subtelny.islands.island.IslandConfiguration;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.utilities.configuration.Configuration;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class IslandConfigurationLoadAction implements LoadAction<IslandConfiguration> {

    private final DSLContext connection;

    private final IslandId islandId;

    public IslandConfigurationLoadAction(DSLContext connection, IslandId islandId) {
        this.connection = connection;
        this.islandId = islandId;
    }

    @Override
    public IslandConfiguration perform() {
        Map<String, String> fields = connection.select(IslandConfigurations.ISLAND_CONFIGURATIONS.CONFIGURATION)
                .from(IslandConfigurations.ISLAND_CONFIGURATIONS)
                .where(IslandConfigurations.ISLAND_CONFIGURATIONS.ISLAND_ID.eq(islandId.getId()))
                .fetchOne(this::getRawConfiguration);
        if (fields == null) {
            return new IslandConfiguration(new Configuration());
        }
        return new IslandConfiguration(new Configuration(fields));
    }

    @Override
    public List<IslandConfiguration> performList() {
        return Collections.singletonList(perform());
    }

    private Map<String, String> getRawConfiguration(Record record) {
        String rawJson = record.get(IslandConfigurations.ISLAND_CONFIGURATIONS.CONFIGURATION).data();
        Gson gson = new Gson();
        Type configurationMap = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(rawJson, configurationMap);
    }

}

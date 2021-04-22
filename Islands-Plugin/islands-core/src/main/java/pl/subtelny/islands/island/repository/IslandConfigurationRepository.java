package pl.subtelny.islands.island.repository;

import org.jooq.DSLContext;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandConfiguration;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.utilities.configuration.Configuration;

@Component
public class IslandConfigurationRepository {

    private final ConnectionProvider connectionProvider;

    @Autowired
    public IslandConfigurationRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public IslandConfiguration loadConfiguration(IslandId islandId) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        return new IslandConfigurationLoadAction(connection, islandId).perform();
    }

    public void saveConfiguration(Island island) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        new IslandConfigurationUpdateAction(connection).perform(island);
    }

}

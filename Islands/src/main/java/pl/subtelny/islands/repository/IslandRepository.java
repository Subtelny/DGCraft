package pl.subtelny.islands.repository;

import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.loader.island.IslandAnemia;
import pl.subtelny.islands.repository.loader.island.IslandAnemiaLoadAction;
import pl.subtelny.islands.repository.loader.island.IslandAnemiaLoadRequest;

import java.util.List;
import java.util.Optional;

@Component
public class IslandRepository {

    private final Configuration configuration;

    public IslandRepository(DatabaseConfiguration databaseConfiguration) {
        this.configuration = databaseConfiguration.getConfiguration();
    }

    public Optional<IslandAnemia> findIsland(IslandId islandId) {
        IslandAnemiaLoadRequest request = IslandAnemiaLoadRequest.newBuilder()
                .where(islandId)
                .build();
        return loadIsland(request);
    }

    private Optional<IslandAnemia> loadIsland(IslandAnemiaLoadRequest request) {
        IslandAnemiaLoadAction loader = new IslandAnemiaLoadAction(configuration, request);
        List<IslandAnemia> loadedData = loader.perform().getLoadedData();
        if (loadedData.size() > 0) {
            return Optional.of(loadedData.get(0));
        }
        return Optional.empty();
    }
}

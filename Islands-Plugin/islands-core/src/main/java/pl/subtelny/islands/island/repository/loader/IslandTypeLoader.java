package pl.subtelny.islands.island.repository.loader;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IslandTypeLoader {

    private final DatabaseConnection databaseConnection;

    public IslandTypeLoader(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Optional<IslandType> findIslandType(IslandId islandId) {
        IslandTypeLoadAction action = getIslandTypeLoadAction(Collections.singletonList(islandId));
        return Optional.ofNullable(action.perform());
    }

    private IslandTypeLoadAction getIslandTypeLoadAction(List<IslandId> islandIds) {
        Configuration configuration = databaseConnection.getConfiguration();
        return new IslandTypeLoadAction(configuration, islandIds);
    }

}

package pl.subtelny.islands.island.query;

import com.github.benmanes.caffeine.cache.CacheLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;

public class IslandTypeCacheLoader implements CacheLoader<IslandId, IslandType> {

    private final ConnectionProvider connectionProvider;

    public IslandTypeCacheLoader(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public @Nullable IslandType load(@NonNull IslandId islandId) {
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        IslandTypeLoadAction action = new IslandTypeLoadAction(islandId, currentConnection);
        return action.perform();
    }

}

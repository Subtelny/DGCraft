package pl.subtelny.islands.skyblockisland.initializer;

import org.bukkit.plugin.Plugin;
import org.jooq.impl.DSL;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandCache;

import java.util.List;


@Component
public class SkyblockIslandInitializer implements DependencyActivator {

    private final SkyblockIslandCache cache;

    private final DatabaseConnection connection;

    @Autowired
    public SkyblockIslandInitializer(SkyblockIslandCache cache, DatabaseConnection connection) {
        this.cache = cache;
        this.connection = connection;
    }

    @Override
    public void activate(Plugin plugin) {
        List<IslandCoordinates> islandCoords = getIslandCoords();
        islandCoords.forEach(cache::removeFreeIslandCoordinates);
    }

    private List<IslandCoordinates> getIslandCoords() {
        return DSL.using(connection.getConfiguration())
                .select(SkyblockIslands.SKYBLOCK_ISLANDS.X, SkyblockIslands.SKYBLOCK_ISLANDS.Z)
                .from(SkyblockIslands.SKYBLOCK_ISLANDS)
                .fetch(record -> new IslandCoordinates(
                        record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X),
                        record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z)));
    }
}

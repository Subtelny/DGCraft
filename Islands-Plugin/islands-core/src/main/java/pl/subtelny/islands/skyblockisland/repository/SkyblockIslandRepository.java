package pl.subtelny.islands.skyblockisland.repository;

import org.bukkit.Location;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.groups.api.GroupsContextService;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoader;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandStorage;
import pl.subtelny.islands.skyblockisland.repository.updater.SkyblockIslandUpdater;
import pl.subtelny.utilities.cuboid.Cuboid;
import java.util.Optional;

@Component
public class SkyblockIslandRepository {

    private final SkyblockIslandLoader loader;

    private final SkyblockIslandStorage storage;

    private final SkyblockIslandUpdater updater;

    @Autowired
    public SkyblockIslandRepository(DatabaseConnection databaseConfiguration,
                                    SkyblockIslandExtendCuboidCalculator extendCuboidCalculator,
                                    GroupsContextService groupsContextService,
                                    TransactionProvider transactionProvider) {
        this.storage = new SkyblockIslandStorage();
        this.loader = new SkyblockIslandLoader(databaseConfiguration, extendCuboidCalculator, transactionProvider, groupsContextService);
        this.updater = new SkyblockIslandUpdater(databaseConfiguration, transactionProvider);
    }

    public SkyblockIsland createIsland(IslandCoordinates islandCoordinates, Location spawn, Cuboid cuboid) {
        SkyblockIsland island = new SkyblockIsland(spawn, cuboid, islandCoordinates);
        SkyblockIslandId islandId = updater.update(island);
        return findSkyblockIsland(islandId).orElseThrow();
    }

    public void saveIsland(SkyblockIsland skyblockIsland) {
        storage.updateIslandCoordinates(skyblockIsland);
        updater.update(skyblockIsland);
    }

    public Optional<SkyblockIsland> findSkyblockIsland(IslandCoordinates islandCoordinates) {
        if (!storage.isIslandCoordinatesFree(islandCoordinates)) {
            Optional<SkyblockIslandId> cache = storage.getCache(islandCoordinates);
            return cache.flatMap(this::findSkyblockIsland).or(Optional::empty);
        }
        return Optional.empty();
    }

    public Optional<SkyblockIsland> findSkyblockIsland(SkyblockIslandId islandId) {
        SkyblockIsland cachedIsland = storage.getCache(islandId, islandId1 -> loader.loadIsland(islandId1).orElse(null));
        storage.updateIslandCoordinates(cachedIsland);
        return Optional.ofNullable(cachedIsland);
    }

    public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
        return storage.nextFreeIslandCoordinates();
    }

    public void removeFreeIslandCoordinate(IslandCoordinates islandCoordinates) {
        storage.removeFreeIslandCoordinates(islandCoordinates);
    }

}

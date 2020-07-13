package pl.subtelny.islands.skyblockisland.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.islandmembership.IslandMembershipRepository;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoader;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandStorage;
import pl.subtelny.islands.skyblockisland.repository.updater.SkyblockIslandUpdater;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class SkyblockIslandRepository {

    private final IslandMembershipRepository islandMembershipRepository;

    private final SkyblockIslandLoader loader;

    private final SkyblockIslandStorage storage;

    private final SkyblockIslandUpdater updater;

    @Autowired
    public SkyblockIslandRepository(IslandMembershipRepository islandMembershipRepository,
                                    DatabaseConnection databaseConfiguration,
                                    IslanderRepository islanderRepository,
                                    SkyblockIslandExtendCuboidCalculator extendCuboidCalculator) {
        this.islandMembershipRepository = islandMembershipRepository;
        this.storage = new SkyblockIslandStorage();
        this.loader = new SkyblockIslandLoader(databaseConfiguration, islanderRepository, islandMembershipRepository, extendCuboidCalculator);
        this.updater = new SkyblockIslandUpdater(databaseConfiguration);
    }

    public CompletableFuture<SkyblockIsland> createIsland(IslandCoordinates islandCoordinates, Cuboid cuboid, Islander owner) {
        SkyblockIsland island = new SkyblockIsland(SkyblockIslandId.of(null), islandCoordinates, cuboid);
        island.changeOwner(owner);
        return updater.updateAsync(island)
                .thenCompose(skyblockIslandId -> CompletableFuture.supplyAsync(() -> findSkyblockIsland(skyblockIslandId).orElseThrow()))
                .thenCompose(skyblockIsland -> CompletableFuture.supplyAsync(() -> {
                    islandMembershipRepository.updateIslandMembership(skyblockIsland);
                    return skyblockIsland;
                }));
    }

    public void saveIslandAsync(SkyblockIsland skyblockIsland) {
        storage.updateIslandCoordinates(skyblockIsland);
        updater.updateAsync(skyblockIsland);
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

package pl.subtelny.islands.skyblockisland.repository;

import org.bukkit.Location;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.islandmembership.IslandMembershipRepository;
import pl.subtelny.islands.skyblockisland.model.MembershipType;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoader;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandStorage;
import pl.subtelny.islands.skyblockisland.repository.updater.SkyblockIslandUpdater;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                                    SkyblockIslandExtendCuboidCalculator extendCuboidCalculator,
                                    TransactionProvider transactionProvider) {
        this.islandMembershipRepository = islandMembershipRepository;
        this.storage = new SkyblockIslandStorage();
        this.loader = new SkyblockIslandLoader(databaseConfiguration, islanderRepository, islandMembershipRepository, extendCuboidCalculator, transactionProvider);
        this.updater = new SkyblockIslandUpdater(databaseConfiguration, transactionProvider);
    }

    public SkyblockIsland createIsland(IslandCoordinates islandCoordinates, Location spawn, Cuboid cuboid, Islander owner) {
        SkyblockIsland island = new SkyblockIsland(null, spawn, cuboid, owner, islandCoordinates);
        SkyblockIslandId islandId = updater.update(island);
        Map<IslanderId, MembershipType> membership = island.getMembers().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getIslanderId(), Map.Entry::getValue));
        islandMembershipRepository.updateIslandMembership(islandId, membership);
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
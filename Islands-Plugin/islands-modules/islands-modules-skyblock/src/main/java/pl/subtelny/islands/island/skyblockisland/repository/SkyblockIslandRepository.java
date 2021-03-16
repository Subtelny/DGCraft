package pl.subtelny.islands.island.skyblockisland.repository;

import org.bukkit.Location;
import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.repository.IslandConfigurationRepository;
import pl.subtelny.islands.island.skyblockisland.IslandCoordinates;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.island.repository.IslandRemoveAction;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemiaFactory;
import pl.subtelny.islands.island.skyblockisland.repository.load.SkyblockIslandLoadRequest;
import pl.subtelny.islands.island.skyblockisland.repository.load.SkyblockIslandLoader;
import pl.subtelny.islands.island.skyblockisland.repository.update.SkyblockIslandAnemiaUpdateAction;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.NullObject;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkyblockIslandRepository {

    private final IslandType islandType;

    private final ConnectionProvider connectionProvider;

    private final SkyblockIslandLoader islandLoader;

    private final SkyblockIslandStorage islandStorage;

    private final IslandMembershipRepository membershipRepository;

    public SkyblockIslandRepository(IslandType islandType,
                                    ConnectionProvider connectionProvider,
                                    IslandExtendCalculator extendCalculator,
                                    IslandMembershipRepository membershipRepository,
                                    IslandConfigurationRepository islandConfigurationRepository) {
        this.islandType = islandType;
        this.connectionProvider = connectionProvider;
        this.membershipRepository = membershipRepository;
        this.islandStorage = new SkyblockIslandStorage();
        this.islandLoader = new SkyblockIslandLoader(connectionProvider, membershipRepository, islandConfigurationRepository, extendCalculator);
    }

    public Optional<SkyblockIsland> findIsland(IslandId islandId) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder(islandType)
                .where(islandId)
                .build();
        Optional<SkyblockIsland> islandOpt = islandStorage.getCache(islandId, islandId1 -> islandLoader.load(request)).get();
        islandOpt.ifPresent(island -> islandStorage.put(island.getIslandCoordinates(), NullObject.of(islandId)));
        return islandOpt;
    }

    public Optional<SkyblockIsland> findIsland(IslandCoordinates islandCoordinates) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder(islandType)
                .where(islandCoordinates)
                .build();
        return islandStorage.getCache(islandCoordinates, islandCoordinates1 -> islandLoader.load(request)).get();
    }

    public Collection<SkyblockIsland> getAllLoadedIslands() {
        return islandStorage.getAllCache().values()
                .stream()
                .map(NullObject::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void removeIsland(SkyblockIsland island) {
        clearIslandMembers(island);
        DSLContext connection = connectionProvider.getCurrentConnection();
        IslandRemoveAction action = new IslandRemoveAction(connection);
        action.perform(island.getId());
        islandStorage.invalidate(island);
    }

    public IslandId createIsland(Location spawn, IslandCoordinates coords, Islander owner) {
        IslandId island = createIsland(spawn, coords);
        IslandMembership islandMembership = IslandMembership.owner(owner.getIslandMemberId(), island);
        membershipRepository.saveIslandMembership(islandMembership);
        return island;
    }

    public IslandId createIsland(Location spawn, IslandCoordinates coords) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia(spawn, coords, islandType);
        return saveIsland(anemia);
    }

    public void saveIsland(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(island);
        saveIsland(anemia);
    }

    private void clearIslandMembers(SkyblockIsland island) {
        membershipRepository.removeIslandMemberships(island.getId());
    }

    private IslandId saveIsland(SkyblockIslandAnemia anemia) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(connection);
        return action.perform(anemia);
    }
}

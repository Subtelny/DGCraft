package pl.subtelny.islands.island.skyblockisland.repository;

import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.Configuration;
import pl.subtelny.islands.island.configuration.ConfigurationReloadable;
import pl.subtelny.islands.island.membership.repository.IslandMembershipLoader;
import pl.subtelny.islands.island.repository.IslandRemoveAction;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islandmember.IslandMemberQueryService;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoadRequest;
import pl.subtelny.utilities.NullObject;

import java.util.Optional;

public class SkyblockIslandRepository {

    private final SkyblockIslandLoader islandLoader;

    private final ConnectionProvider connectionProvider;

    private final SkyblockIslandStorage islandStorage;

    public SkyblockIslandRepository(ConnectionProvider connectionProvider,
                                    Configuration<SkyblockIslandConfiguration> configuration,
                                    IslandMemberQueryService islandMemberQueryService,
                                    IslandMembershipLoader islandMembershipLoader) {
        this.connectionProvider = connectionProvider;
        this.islandStorage = new SkyblockIslandStorage();
        this.islandLoader = new SkyblockIslandLoader(connectionProvider, islandMemberQueryService, islandMembershipLoader, configuration);
    }

    public Optional<SkyblockIsland> findIsland(IslandId islandId, IslandType islandType) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder(islandType)
                .where(islandId)
                .build();
        return islandStorage.getCache(islandId, islandId1 -> islandLoader.load(request)).get();
    }

    public Optional<SkyblockIsland> findIsland(IslandCoordinates islandCoordinates, IslandType islandType) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder(islandType)
                .where(islandCoordinates)
                .build();
        return islandStorage.getCache(islandCoordinates, islandCoordinates1 -> islandLoader.load(request)).get();
    }

    public IslandId saveIsland(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(island);
        DSLContext connection = connectionProvider.getCurrentConnection();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(connection);
        IslandId islandId = action.perform(anemia);
        islandStorage.put(island.getIslandCoordinates(), NullObject.of(islandId));
        return islandId;
    }

    public void removeIsland(SkyblockIsland island) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        IslandRemoveAction action = new IslandRemoveAction(connection);
        action.perform(island.getId());
        islandStorage.invalidate(island);
    }
}

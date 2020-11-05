package pl.subtelny.islands.island.skyblockisland.repository;

import org.bukkit.Location;
import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.repository.IslandRemoveAction;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.island.IslandCoordinates;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemiaFactory;
import pl.subtelny.islands.island.skyblockisland.repository.load.SkyblockIslandLoadRequest;
import pl.subtelny.islands.island.skyblockisland.repository.load.SkyblockIslandLoader;
import pl.subtelny.islands.island.skyblockisland.repository.update.SkyblockIslandAnemiaUpdateAction;
import pl.subtelny.utilities.NullObject;

import java.util.Optional;

public class SkyblockIslandRepository {

    private final IslandType islandType;

    private final SkyblockIslandLoader islandLoader;

    private final ConnectionProvider connectionProvider;

    private final SkyblockIslandStorage islandStorage;

    public SkyblockIslandRepository(IslandType islandType,
                                    ConnectionProvider connectionProvider,
                                    IslandMemberQueryService islandMemberQueryService,
                                    IslandMembershipQueryService islandMembershipLoader,
                                    IslandExtendCalculator extendCalculator) {
        this.islandType = islandType;
        this.connectionProvider = connectionProvider;
        this.islandStorage = new SkyblockIslandStorage();
        this.islandLoader = new SkyblockIslandLoader(connectionProvider, islandMemberQueryService, islandMembershipLoader, extendCalculator);
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

    public void removeIsland(SkyblockIsland island) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        IslandRemoveAction action = new IslandRemoveAction(connection);
        action.perform(island.getId());
        islandStorage.invalidate(island);
    }

    public IslandId createIsland(Location spawn, IslandCoordinates coords) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia(spawn, coords, islandType);
        return saveIsland(anemia);
    }

    public IslandId saveIsland(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(island);
        return saveIsland(anemia);
    }

    private IslandId saveIsland(SkyblockIslandAnemia anemia) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(connection);
        IslandId islandId = action.perform(anemia);
        islandStorage.put(anemia.getIslandCoordinates(), NullObject.of(islandId));
        return islandId;
    }
}

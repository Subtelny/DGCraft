package pl.subtelny.islands.islander.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.query.IslandQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.storage.IslanderCacheLoader;
import pl.subtelny.islands.islander.repository.storage.IslanderStorage;
import pl.subtelny.islands.islander.repository.updater.IslanderUpdater;
import pl.subtelny.utilities.NullObject;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class IslanderRepository {

    private final IslanderStorage islanderStorage;

    private final IslanderUpdater islanderUpdater;

    private final IslandQueryService islandQueryService;

    @Autowired
    public IslanderRepository(ConnectionProvider connectionProvider, IslandQueryService islandQueryService, IslandMembershipQueryService islandMembershipQueryService) {
        this.islandQueryService = islandQueryService;
        this.islanderStorage = new IslanderStorage(new IslanderCacheLoader(connectionProvider, islandQueryService, islandMembershipQueryService));
        this.islanderUpdater = new IslanderUpdater(connectionProvider);
    }

    public Optional<Islander> getIslanderIfPresent(IslanderId islanderId) {
        return islanderStorage.getCacheIfPresent(islanderId).flatMap(NullObject::get);
    }

    public Optional<Islander> findIslander(IslanderId islanderId) {
        return islanderStorage.getCache(islanderId).get();
    }

    public void createIslander(IslanderId islanderId) {
        Islander islander = new Islander(islanderId, new ArrayList<>(), islandQueryService);
        updateIslander(islander);
    }

    public void updateIslander(Islander islander) {
        islanderStorage.put(islander.getIslanderId(), NullObject.of(islander));
        islanderUpdater.performAction(islander);
    }

}

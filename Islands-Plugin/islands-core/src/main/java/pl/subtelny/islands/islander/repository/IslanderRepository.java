package pl.subtelny.islands.islander.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.loader.IslanderLoadRequest;
import pl.subtelny.islands.islander.repository.loader.IslanderLoader;
import pl.subtelny.islands.islander.repository.storage.IslanderStorage;
import pl.subtelny.islands.islander.repository.updater.IslanderUpdater;
import pl.subtelny.utilities.NullObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IslanderRepository {

    private final IslanderStorage islanderStorage;

    private final IslanderLoader islanderLoader;

    private final IslanderUpdater islanderUpdater;

    @Autowired
    public IslanderRepository(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        this.islanderStorage = new IslanderStorage();
        this.islanderLoader = new IslanderLoader(databaseConfiguration);
        this.islanderUpdater = new IslanderUpdater(databaseConfiguration, transactionProvider);
    }

    public Optional<Islander> getIslanderIfPresent(IslanderId islanderId) {
        return islanderStorage.getCacheIfPresent(islanderId).flatMap(NullObject::get);
    }

    public List<Islander> findIslanders(List<IslanderId> islanderIds) {
        return islanderStorage.getAllCache(islanderIds, islanderIds1 -> {
            IslanderLoadRequest request = IslanderLoadRequest.newBuilder()
                    .where(islanderIds)
                    .build();
            List<Islander> islanders = islanderLoader.loadIslanders(request);
            return mapIslanderIdsIntoNullObjects(islanderIds, islanders);
        }).values().stream()
                .map(NullObject::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Map<IslanderId, NullObject<Islander>> mapIslanderIdsIntoNullObjects(List<IslanderId> islanderIds, List<Islander> islanders) {
        return islanderIds.stream()
                .collect(Collectors.toMap(islanderId -> islanderId, islanderId -> findIslanderInList(islanderId, islanders)));
    }

    private NullObject<Islander> findIslanderInList(IslanderId islanderId, List<Islander> islanders) {
        Optional<Islander> islanderOpt = islanders.stream()
                .filter(islander -> islander.getIslanderId().equals(islanderId))
                .findAny();
        return islanderOpt.map(NullObject::of).orElse(NullObject.empty());
    }

    public Optional<Islander> findIslander(IslanderId islanderId) {
        return islanderStorage.getCache(islanderId, islanderId1 -> {
            IslanderLoadRequest request = IslanderLoadRequest.newBuilder()
                    .where(islanderId1)
                    .build();
            Optional<Islander> islanderOpt = islanderLoader.loadIslander(request);
            return islanderOpt.map(NullObject::of).orElse(NullObject.empty());
        }).get();
    }

    public void updateIslander(Islander islander) {
        islanderStorage.put(islander.getIslanderId(), NullObject.of(islander));
        islanderUpdater.performActionAsync(islander);
    }

}

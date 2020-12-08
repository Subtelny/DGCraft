package pl.subtelny.islands.island;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;

import java.util.concurrent.CompletableFuture;

@Component
public class IslandCommandService extends IslandService {

    private final TransactionProvider transactionProvider;

    @Autowired
    public IslandCommandService(IslandModules islandModules,
                                IslandIdToIslandTypeService islandIdToIslandTypeCache,
                                TransactionProvider transactionProvider) {
        super(islandModules, islandIdToIslandTypeCache);
        this.transactionProvider = transactionProvider;
    }

    public CompletableFuture<Island> createIsland(IslandType islandType, IslandCreateRequest request) {
        IslandModule<Island> islandModule = getIslandModule(islandType);
        return transactionProvider.transactionResultAsync(() -> {
            Island island = islandModule.createIsland(request);
            updateCache(island);
            return island;
        }).toCompletableFuture();
    }

    public void removeIsland(Island island) {
        IslandModule<Island> islandModule = getIslandModule(island);
        islandModule.removeIsland(island);
        invalidateCache(island.getId());
    }

    public void saveIsland(Island island) {
        IslandModule<Island> islandModule = getIslandModule(island);
        islandModule.saveIsland(island);
    }

    private IslandModule<Island> getIslandModule(Island island) {
        IslandType islandType = island.getIslandType();
        return getIslandModule(islandType);
    }

    private IslandModule<Island> getIslandModule(IslandType islandType) {
        return findIslandModule(islandType)
                .orElseThrow(() -> new IllegalStateException("Not found IslandModule for type " + islandType.getInternal()));
    }

}

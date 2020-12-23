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
                                TransactionProvider transactionProvider) {
        super(islandModules);
        this.transactionProvider = transactionProvider;
    }

    public CompletableFuture<Island> createIsland(IslandType islandType, IslandCreateRequest request) {
        IslandModule<Island> islandModule = getIslandModule(islandType);
        return transactionProvider.transactionResultAsync(() -> islandModule.createIsland(request)).toCompletableFuture();
    }

    public void removeIsland(Island island) {
        IslandModule<Island> islandModule = getIslandModule(island);
        islandModule.removeIsland(island);
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

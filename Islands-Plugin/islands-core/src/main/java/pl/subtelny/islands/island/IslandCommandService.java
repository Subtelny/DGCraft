package pl.subtelny.islands.island;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;

@Component
public class IslandCommandService extends IslandService {

    @Autowired
    public IslandCommandService(IslandModules islandModules, IslandIdToIslandTypeService islandIdToIslandTypeCache) {
        super(islandModules, islandIdToIslandTypeCache);
    }

    public Island createIsland(IslandType islandType, IslandCreateRequest request) {
        IslandModule<Island> islandModule = getIslandModule(islandType);
        Island island = islandModule.createIsland(request);
        updateCache(island);
        return island;
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

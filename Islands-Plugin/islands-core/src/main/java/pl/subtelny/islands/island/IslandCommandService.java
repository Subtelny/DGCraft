package pl.subtelny.islands.island;

import org.bukkit.World;
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

    public Island createIsland(IslandType islandType) {
        IslandModule<Island> islandModule = getIslandModule(islandType);
        return islandModule.createIsland();
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
        World world = island.getWorld();
        return getIslandModule(world);
    }

    private IslandModule<Island> getIslandModule(IslandType islandType) {
        return findIslandModule(islandType)
                .orElseThrow(() -> new IllegalStateException("Not found IslandModule for type " + islandType.getInternal()));
    }

    private IslandModule<Island> getIslandModule(World world) {
        return findIslandModule(world)
                .orElseThrow(() -> new IllegalStateException("Not found IslandModule for world " + world));
    }

}

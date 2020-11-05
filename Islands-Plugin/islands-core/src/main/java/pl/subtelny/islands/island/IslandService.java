package pl.subtelny.islands.island;

import org.bukkit.World;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;

import java.util.Optional;

public abstract class IslandService {

    private final IslandModules islandModules;

    private final IslandIdToIslandTypeService islandIdToIslandTypeCache;

    protected IslandService(IslandModules islandModules, IslandIdToIslandTypeService islandIdToIslandTypeCache) {
        this.islandModules = islandModules;
        this.islandIdToIslandTypeCache = islandIdToIslandTypeCache;
    }

    protected IslandType getIslandType(IslandId islandId) {
        return islandIdToIslandTypeCache.getIslandType(islandId);
    }

    protected void invalidateCache(IslandId islandId) {
        islandIdToIslandTypeCache.invalidate(islandId);
    }

    protected void updateCache(Island island) {
        islandIdToIslandTypeCache.update(island);
    }

    protected Optional<IslandModule<Island>> findIslandModule(IslandType islandType) {
        return islandModules.findIslandModule(islandType);
    }

    protected Optional<IslandModule<Island>> findIslandModule(World world) {
        return islandModules.findIslandModule(world);
    }

}

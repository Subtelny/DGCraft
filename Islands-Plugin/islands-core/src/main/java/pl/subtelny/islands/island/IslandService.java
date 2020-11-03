package pl.subtelny.islands.island;

import org.bukkit.World;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;

import java.util.Optional;

public abstract class IslandService {

    private final IslandModules islandModules;

    protected final IslandIdToIslandTypeService islandIdToIslandTypeCache;

    protected IslandService(IslandModules islandModules, IslandIdToIslandTypeService islandIdToIslandTypeCache) {
        this.islandModules = islandModules;
        this.islandIdToIslandTypeCache = islandIdToIslandTypeCache;
    }

    protected void invalidateCache(IslandId islandId) {
        islandIdToIslandTypeCache.invalidate(islandId);
    }

    protected Optional<IslandModule<Island>> findIslandModule(IslandType islandType) {
        return islandModules.getIslandModules().stream()
                .filter(islandModule -> islandModule.getType().equals(islandType))
                .findFirst();
    }

    protected Optional<IslandModule<Island>> findIslandModule(World world) {
        return islandModules.getIslandModules().stream()
                .filter(islandModule -> islandModule.getWorld().equals(world))
                .findFirst();
    }

}

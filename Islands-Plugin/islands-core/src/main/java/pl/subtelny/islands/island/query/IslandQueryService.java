package pl.subtelny.islands.island.query;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.island.module.IslandModules;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IslandQueryService extends IslandService {

    @Autowired
    public IslandQueryService(IslandModules islandModules, IslandIdToIslandTypeService islandIdToIslandTypeCache) {
        super(islandModules, islandIdToIslandTypeCache);
    }

    public List<Island> getIslands(List<IslandId> islandIds) {
        return islandIds.stream()
                .map(this::findIsland)
                .filter(islandFindResult -> islandFindResult.getResult().isPresent())
                .map(islandFindResult -> islandFindResult.getResult().get())
                .collect(Collectors.toList());
    }

    public IslandFindResult findIsland(IslandId islandId) {
        IslandType islandType = islandIdToIslandTypeCache.getIslandType(islandId);
        return findIslandModule(islandType)
                .map(islandModule -> islandModule.findIsland(islandId))
                .map(island -> IslandFindResult.of(island.orElse(null)))
                .orElseGet(IslandFindResult::notIslandWorld);
    }

    public IslandFindResult findIsland(Location location) {
        World world = location.getWorld();
        return findIslandModule(world)
                .map(islandModule -> islandModule.findIsland(location))
                .map(island -> IslandFindResult.of(island.orElse(null)))
                .orElseGet(IslandFindResult::notIslandWorld);
    }

    public boolean isIslandWorld(World world) {
        return findIslandModule(world).isPresent();
    }

}

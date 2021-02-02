package pl.subtelny.islands.island.cqrs.query;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.island.cqrs.IslandService;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IslandQueryService extends IslandService {

    @Autowired
    public IslandQueryService(IslandModules islandModules) {
        super(islandModules);
    }

    public List<Island> getIslands(List<IslandId> islandIds) {
        return islandIds.stream()
                .flatMap(islandId -> findIsland(islandId).stream())
                .collect(Collectors.toList());
    }

    public Optional<Island> findIsland(IslandId islandId) {
        IslandType islandType = islandId.getIslandType();
        return getIslandIslandModule(islandType)
                .findIsland(islandId);
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

    private IslandModule<Island> getIslandIslandModule(IslandType islandType) {
        return findIslandModule(islandType)
                .orElseThrow(() -> ValidationException.of("island.query.island_module_not_found", islandType));
    }

}

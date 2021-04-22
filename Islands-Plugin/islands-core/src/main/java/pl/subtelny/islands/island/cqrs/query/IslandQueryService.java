package pl.subtelny.islands.island.cqrs.query;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.IslandService;
import pl.subtelny.islands.island.crates.IslandCrates;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;

import java.util.Optional;

@Component
public class IslandQueryService extends IslandService {

    @Autowired
    public IslandQueryService(IslandModules islandModules) {
        super(islandModules);
    }

    public IslandCrates getIslandCrates(IslandType islandType) {
        IslandModule<Island> islandModule = getIslandModule(islandType);
        return islandModule.getIslandCrates();
    }

    public Optional<Island> findIsland(IslandId islandId) {
        IslandType islandType = islandId.getIslandType();
        return getIslandModule(islandType)
                .findIsland(islandId);
    }

    public IslandFindResult findIsland(Location location) {
        World world = location.getWorld();
        return findIslandModule(world)
                .map(islandModule -> islandModule.findIsland(location))
                .map(island -> IslandFindResult.of(island.orElse(null)))
                .orElseGet(IslandFindResult::notIslandWorld);
    }

}

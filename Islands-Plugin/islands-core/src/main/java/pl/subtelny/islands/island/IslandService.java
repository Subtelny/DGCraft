package pl.subtelny.islands.island;

import org.bukkit.World;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;

import java.util.Optional;

public abstract class IslandService {

    private final IslandModules islandModules;

    protected IslandService(IslandModules islandModules) {
        this.islandModules = islandModules;
    }

    protected Optional<IslandModule<Island>> findIslandModule(IslandType islandType) {
        return islandModules.findIslandModule(islandType);
    }

    protected Optional<IslandModule<Island>> findIslandModule(World world) {
        return islandModules.findIslandModule(world);
    }

}

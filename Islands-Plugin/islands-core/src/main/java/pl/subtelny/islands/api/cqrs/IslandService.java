package pl.subtelny.islands.api.cqrs;

import org.bukkit.World;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.utilities.exception.ValidationException;

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

    protected Island getIsland(IslandMember islandMember, IslandType islandType) {
        return islandMember.getIsland(islandType)
                .map(this::getIsland)
                .orElseThrow(() -> ValidationException.of("islandService.island.not_found", islandType.getInternal()));
    }

    protected Island getIsland(IslandId islandId) {
        IslandModule<Island> islandModule = getIslandModule(islandId.getIslandType());
        return islandModule.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("islandService.island.not_found", islandId.getIslandType().getInternal()));
    }

    protected IslandModule<Island> getIslandModule(IslandType islandType) {
        return findIslandModule(islandType)
                .orElseThrow(() -> new IllegalStateException("Not found IslandModule for type " + islandType.getInternal()));
    }

}

package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.flags.IslandFlags;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class IslandBuildActionGuard extends ActionGuard {

    public static final String BUILD_BYPASS_PERMISSION = "dgcraft.islands.build.bypass";

    IslandBuildActionGuard(IslandQueryService islandQueryService,
                                  IslanderQueryService islanderQueryService) {
        super(islandQueryService, islanderQueryService);
    }

    public IslandActionGuardResult accessToMultiBuild(Location from, List<Location> blocks) {
        IslandFindResult result = islandQueryService.findIsland(from);
        return result.getResult()
                .filter(island -> !containAllBlocks(island, blocks))
                .map(island -> IslandActionGuardResult.ACTION_PROHIBITED)
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    public IslandActionGuardResult accessToSpreadBlock(Location source, Location target) {
        IslandFindResult sourceResult = islandQueryService.findIsland(source);
        return sourceResult.getResult()
                .filter(island -> !island.getCuboid().contains(target))
                .map(island -> IslandActionGuardResult.ACTION_PROHIBITED)
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    public IslandActionGuardResult accessToBuild(Player player, Location location) {
        if (player.hasPermission(BUILD_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return playerHasAccessToBuild(player, location);
    }

    public IslandActionGuardResult accessToExplodeAndValidateBlocks(Location source, List<Block> blocks) {
        IslandFindResult result = islandQueryService.findIsland(source);
        Optional<Island> islandOpt = result.getResult()
                .filter(island -> IslandFlags.EXPLODE.getValue(island.getConfiguration()));
        if (islandOpt.isPresent()) {
            Island island = islandOpt.get();
            removeNonMatchingBlocksIntoIsland(island, blocks);
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return IslandActionGuardResult.ACTION_PERMITTED;
    }

    private IslandActionGuardResult playerHasAccessToBuild(Player player, Location location) {
        IslandFindResult result = islandQueryService.findIsland(location);
        if (result.isNotIslandWorld()) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return result.getResult()
                .filter(island -> playerHasAccessToBuild(player, island))
                .map(island -> IslandActionGuardResult.ACTION_PERMITTED)
                .orElse(IslandActionGuardResult.ACTION_PROHIBITED);
    }

    private void removeNonMatchingBlocksIntoIsland(Island island, List<Block> blocks) {
        Cuboid islandCuboid = island.getCuboid();
        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block next = iterator.next();
            Location location = next.getLocation();
            if (!islandCuboid.contains(location)) {
                iterator.remove();
            }
        }
    }

    private boolean playerHasAccessToBuild(Player player, Island island) {
        Islander islander = islanderQueryService.getIslander(player);
        return island.isMember(islander);
    }

    private boolean containAllBlocks(Island island, List<Location> blockStates) {
        Cuboid cuboid = island.getCuboid();
        return blockStates.stream()
                .allMatch(cuboid::contains);
    }

}

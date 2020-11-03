package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.islandold.IslandsQueryService;
import pl.subtelny.islands.island.query.IslandFindResult;
import pl.subtelny.islands.islander.IslanderService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class IslandActionGuard {

    public static final String INTERACT_BYPASS_PERMISSION = "dgcraft.islands.interact.bypass";

    public static final String ATTACK_BYPASS_PERMISSION = "dgcraft.islands.attack.bypass";

    public static final String BUILD_BYPASS_PERMISSION = "dgcraft.islands.build.bypass";

    public static final String ENTER_BYPASS_PERMISSION = "dgcraft.islands.enter.bypass";

    private final IslandsQueryService islandService;

    private final IslanderService islanderService;

    @Autowired
    public IslandActionGuard(IslandsQueryService islandService, IslanderService islanderService) {
        this.islandService = islandService;
        this.islanderService = islanderService;
    }

    public IslandActionGuardResult accessToEnter(Player player, Location location) {
        if (player.hasPermission(ENTER_BYPASS_PERMISSION)) {
            if (!islandService.isIslandWorld(location)) {
                return IslandActionGuardResult.NOT_ISLAND_WORLD;
            }
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        return playerHasAccessToLocation(player, location);
    }

    public IslandActionGuardResult accessToSpreadBlock(Location source, Location target) {
        IslandFindResult islandSourceResult = islandService.findIslandAtLocation(source);
        IslandActionGuardResult sourceResult = locationInIsland(source, islandSourceResult);
        if (sourceResult.isActionPermited()) {
            Optional<Island> sourceIslandOpt = islandSourceResult.getResult();
            if (sourceIslandOpt.isPresent()) {
                return locationMatchIsland(target, sourceIslandOpt.get());
            }
        }
        return sourceResult;
    }

    private IslandActionGuardResult locationInIsland(Location location, IslandFindResult result) {
        if (result.isNotIslandWorld()) {
            return IslandActionGuardResult.NOT_ISLAND_WORLD;
        }
        Optional<Island> islandOpt = result.getResult();
        if (islandOpt.isEmpty()) {
            return IslandActionGuardResult.ACTION_PROHIBITED;
        }
        return locationMatchIsland(location, islandOpt.get());
    }

    private IslandActionGuardResult locationMatchIsland(Location location, Island island) {
        boolean isInIsland = island.getCuboid().contains(location);
        if (isInIsland) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        return IslandActionGuardResult.ACTION_PROHIBITED;
    }

    public IslandActionGuardResult accessToExplodeAndValidateBlocks(Location source, List<Block> blocks) {
        IslandFindResult islandAtLocation = islandService.findIslandAtLocation(source);
        IslandActionGuardResult result = locationInIsland(source, islandAtLocation);
        if (result.isActionPermited()) {
            islandAtLocation.getResult()
                    .ifPresent(island -> removeNonMatchingBlocksIntoIsland(island, blocks));
        }
        return result;
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

    public IslandActionGuardResult accessToInteract(Entity entity, Entity toInteract) {
        if (entity.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        Location location = toInteract.getLocation();
        return isPlayerWithAccessToLocation(entity, location);
    }

    public IslandActionGuardResult accessToHit(Entity attacker, Entity victim) {
        if (attacker.hasPermission(ATTACK_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        Location location = victim.getLocation();
        return isPlayerWithAccessToLocation(attacker, location);
    }

    private IslandActionGuardResult isPlayerWithAccessToLocation(Entity entity, Location location) {
        if (isPlayer(entity)) {
            Player player = (Player) entity;
            return playerHasAccessToLocation(player, location);
        }
        return IslandActionGuardResult.ACTION_PERMITED;
    }

    private boolean isPlayer(Entity entity) {
        return entity.getType() == EntityType.PLAYER;
    }

    public IslandActionGuardResult accessToBuild(Player player, Location location) {
        if (player.hasPermission(BUILD_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        return playerHasAccessToLocation(player, location);
    }

    private IslandActionGuardResult playerHasAccessToLocation(Player player, Location location) {
        IslandFindResult islandFindResult = islandService.findIslandAtLocation(location);
        if (islandFindResult.isNotIslandWorld()) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        Optional<Island> islandOpt = islandFindResult.getResult();
        Boolean hasAccess = islandOpt.map(island -> playerHasAccessToBuildOnIsland(player, location, island)).orElse(false);
        if (hasAccess) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        return IslandActionGuardResult.ACTION_PROHIBITED;
    }

    private boolean playerHasAccessToBuildOnIsland(Player player, Location location, Island island) {
        Islander islander = islanderService.getIslander(player);
        if (island.isMemberOfIsland(islander)) {
            return island.getCuboid().contains(location);
        }
        return false;
    }

}

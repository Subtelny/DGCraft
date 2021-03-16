package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandConfiguration;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.configuration.datatype.BooleanDataType;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.entity.EntityTypeUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class IslandActionGuard {

    private static final String INTERACT_BYPASS_PERMISSION = "dgcraft.islands.interact.bypass";

    private static final String ATTACK_BYPASS_PERMISSION = "dgcraft.islands.attack.bypass";

    public static final String BUILD_BYPASS_PERMISSION = "dgcraft.islands.build.bypass";

    private static final String ENTER_BYPASS_PERMISSION = "dgcraft.islands.enter.bypass";

    private static final String SPAWN_AGGRESSIVE_MOB_DISABLED_KEY = "SPAWN_AGGRESSIVE_MOB_DISABLED";

    private static final String SPAWN_PASSIVE_MOB_DISABLED_KEY = "SPAWN_PASSIVE_MOB_DISABLED";

    private final IslandQueryService islandQueryService;

    private final IslanderQueryService islanderQueryService;

    @Autowired
    public IslandActionGuard(IslandQueryService islandQueryService, IslanderQueryService islanderService) {
        this.islandQueryService = islandQueryService;
        this.islanderQueryService = islanderService;
    }

    public IslandActionGuardResult accessToMultiBuild(Location from, List<Location> blocks) {
        IslandFindResult result = islandQueryService.findIsland(from);
        if (result.isNotIslandWorld()) {
            return IslandActionGuardResult.NOT_ISLAND_WORLD;
        }
        return result.getResult()
                .filter(island -> containAllBlocks(island, blocks))
                .map(island -> IslandActionGuardResult.ACTION_PERMITTED)
                .orElse(IslandActionGuardResult.ACTION_PROHIBITED);
    }

    public IslandActionGuardResult accessToItem(Entity entity, Location location) {
        if (entity.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        IslandFindResult result = islandQueryService.findIsland(location);
        return result.getResult()
                .map(island -> entityAccessToItem(entity, island))
                .orElse(IslandActionGuardResult.NOT_ISLAND_WORLD);
    }

    public IslandActionGuardResult accessToEnter(Player player, Location location) {
        if (player.hasPermission(ENTER_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return playerHasAccessToLocation(player, location);
    }

    public IslandActionGuardResult accessToSpawn(Entity entity, Location location) {
        IslandFindResult islandAtLocation = islandQueryService.findIsland(location);
        IslandActionGuardResult result = locationInIsland(location, islandAtLocation);
        if (result.isActionPermitted()) {
            EntityType entityType = entity.getType();
            return canSpawn(islandAtLocation.getResult().get(), entityType);
        }
        return result;
    }

    public IslandActionGuardResult accessToSpreadBlock(Location source, Location target) {
        IslandFindResult islandSourceResult = islandQueryService.findIsland(source);
        IslandActionGuardResult sourceResult = locationInIsland(source, islandSourceResult);
        if (sourceResult.isActionPermitted()) {
            Optional<Island> sourceIslandOpt = islandSourceResult.getResult();
            if (sourceIslandOpt.isPresent()) {
                return locationMatchIsland(target, sourceIslandOpt.get());
            }
        }
        return sourceResult;
    }

    public IslandActionGuardResult accessToExplodeAndValidateBlocks(Location source, List<Block> blocks) {
        IslandFindResult islandAtLocation = islandQueryService.findIsland(source);
        IslandActionGuardResult result = locationInIsland(source, islandAtLocation);
        if (result.isActionPermitted()) {
            islandAtLocation.getResult()
                    .ifPresent(island -> removeNonMatchingBlocksIntoIsland(island, blocks));
        }
        return result;
    }

    public IslandActionGuardResult accessToInteract(Entity entity, Entity toInteract) {
        if (entity.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        Location location = toInteract.getLocation();
        return isPlayerWithAccessToLocation(entity, location);
    }

    public IslandActionGuardResult accessToHit(Entity attacker, Entity victim) {
        if (attacker.hasPermission(ATTACK_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        Location location = victim.getLocation();
        return isPlayerWithAccessToLocation(attacker, location);
    }

    public IslandActionGuardResult accessToBuild(Player player, Location location) {
        if (player.hasPermission(BUILD_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return playerHasAccessToLocation(player, location);
    }

    private boolean containAllBlocks(Island island, List<Location> blockStates) {
        Cuboid cuboid = island.getCuboid();
        return blockStates.stream()
                .allMatch(cuboid::contains);
    }

    private IslandActionGuardResult entityAccessToItem(Entity entity, Island island) {
        if (EntityType.PLAYER.equals(entity.getType())) {
            Islander islander = islanderQueryService.getIslander((Player) entity);
            if (island.isMember(islander)) {
                return IslandActionGuardResult.ACTION_PERMITTED;
            }
        }
        return IslandActionGuardResult.ACTION_PROHIBITED;
    }

    private IslandActionGuardResult canSpawn(Island island, EntityType entityType) {
        IslandConfiguration configuration = island.getConfiguration();
        boolean rejectSpawn = false;
        if (EntityTypeUtils.isAggressive(entityType)) {
            rejectSpawn = configuration.getValue(SPAWN_AGGRESSIVE_MOB_DISABLED_KEY, BooleanDataType.TYPE);
        } else if (EntityTypeUtils.isPassive(entityType)) {
            rejectSpawn = configuration.getValue(SPAWN_PASSIVE_MOB_DISABLED_KEY, BooleanDataType.TYPE);
        }
        return rejectSpawn ? IslandActionGuardResult.ACTION_PROHIBITED : IslandActionGuardResult.ACTION_PERMITTED;
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

    private IslandActionGuardResult locationInIsland(Location location, IslandFindResult result) {
        if (result.isNotIslandWorld()) {
            return IslandActionGuardResult.NOT_ISLAND_WORLD;
        }
        Optional<Island> islandOpt = result.getResult();
        return islandOpt
                .map(island -> locationMatchIsland(location, islandOpt.get()))
                .orElse(IslandActionGuardResult.ACTION_PROHIBITED);
    }

    private IslandActionGuardResult locationMatchIsland(Location location, Island island) {
        boolean isInIsland = island.getCuboid().contains(location);
        if (isInIsland) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return IslandActionGuardResult.ACTION_PROHIBITED;
    }

    private IslandActionGuardResult isPlayerWithAccessToLocation(Entity entity, Location location) {
        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            return playerHasAccessToLocation(player, location);
        }
        return IslandActionGuardResult.ACTION_PERMITTED;
    }

    private IslandActionGuardResult playerHasAccessToLocation(Player player, Location location) {
        IslandFindResult islandFindResult = islandQueryService.findIsland(location);
        if (islandFindResult.isNotIslandWorld()) {
            return IslandActionGuardResult.NOT_ISLAND_WORLD;
        }
        Optional<Island> islandOpt = islandFindResult.getResult();
        boolean hasAccess = islandOpt.map(island -> playerHasAccessToBuildOnIsland(player, location, island)).orElse(false);
        return hasAccess ? IslandActionGuardResult.ACTION_PERMITTED : IslandActionGuardResult.ACTION_PROHIBITED;
    }

    private boolean playerHasAccessToBuildOnIsland(Player player, Location location, Island island) {
        Islander islander = islanderQueryService.getIslander(player);
        if (island.isMember(islander)) {
            return island.getCuboid().contains(location);
        }
        return false;
    }

}

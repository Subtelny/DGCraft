package pl.subtelny.islands.service;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class IslandActionGuard {

    public static final String INTERACT_BYPASS_PERMISSION = "dgcraft.islands.interact.bypass";

    public static final String ATTACK_BYPASS_PERMISSION = "dgcraft.islands.attack.bypass";

    public static final String BUILD_BYPASS_PERMISSION = "dgcraft.islands.build.bypass";

    private final IslandService islandService;

    public IslandActionGuard(IslandService islandService) {
        this.islandService = islandService;
    }

    public IslandActionGuardResult accessToExplodeAndValidateBlocks(Location source, List<Block> blocks) {
        IslandFindResult islandAtLocation = islandService.findIslandAtLocation(source);
        if (islandAtLocation.isNotIslandWorld()) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        if (islandAtLocation.isLoading()) {
            return IslandActionGuardResult.ISLAND_LOADING;
        }
        Optional<Island> islandOpt = getIslandFromResult(islandAtLocation);
        if (islandOpt.isEmpty()) {
            return accessToOutsideOfIsland(source);
        }
        Island island = islandOpt.get();
        removeNonMatchingBlocksIntoIsland(island, blocks);
        return IslandActionGuardResult.ACTION_PERMITED;
    }

    private void removeNonMatchingBlocksIntoIsland(Island island, List<Block> blocks) {
        Cuboid islandCuboid = island.getCuboid();
        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block next = iterator.next();
            Location location = next.getLocation();
            if (!islandCuboid.containsLocation(location)) {
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

    private IslandActionGuardResult playerHasAccessToLocation(Player entity, Location location) {
        IslandFindResult islandFindResult = islandService.findIslandAtLocation(location);
        if (islandFindResult.isNotIslandWorld()) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        if (islandFindResult.isLoading()) {
            return IslandActionGuardResult.ISLAND_LOADING;
        }
        if (playerIsInIsland(entity, islandFindResult)) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        return IslandActionGuardResult.ACTION_PROHIBITED;
    }

    private IslandActionGuardResult accessToOutsideOfIsland(Location location) {
        World world = location.getWorld();
        World skyblockIslandWorld = Settings.SkyblockIsland.ISLAND_WORLD;
        if (skyblockIslandWorld.equals(world)) {
            return IslandActionGuardResult.ACTION_PROHIBITED;
        }
        return IslandActionGuardResult.ACTION_PERMITED;
    }

    private boolean playerIsInIsland(Player player, IslandFindResult islandFindResult) {
        Optional<Island> islandOpt = getIslandFromResult(islandFindResult);
        if (islandOpt.isPresent()) {
            Island island = islandOpt.get();
            return islandService.isInIsland(player, island);
        }
        return false;
    }

    private Optional<Island> getIslandFromResult(IslandFindResult result) {
        CompletableFuture<Optional<Island>> future = result.getIsland();
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw ValidationException.of("There is problem with load island");
        }
    }

}

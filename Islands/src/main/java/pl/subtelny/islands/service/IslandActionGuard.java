package pl.subtelny.islands.service;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.validation.ValidationException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class IslandActionGuard {

    private final IslandService islandService;

    public IslandActionGuard(IslandService islandService) {
        this.islandService = islandService;
    }

    public IslandActionGuardResult accessToInteract(Entity entity, Entity toInteract) {
        if (entity.hasPermission("dgcraft.islands.interact.bypass")) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        Location location = toInteract.getLocation();
        return isPlayerWithAccessToLocation(entity, location);
    }

    public IslandActionGuardResult accessToHit(Entity attacker, Entity victim) {
        if (attacker.hasPermission("dgcraft.islands.attack.bypass")) {
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
        if (player.hasPermission("dgcraft.islands.build.bypass")) {
            return IslandActionGuardResult.ACTION_PERMITED;
        }
        return playerHasAccessToLocation(player, location);
    }

    private IslandActionGuardResult playerHasAccessToLocation(Player entity, Location location) {
        IslandFindResult islandFindResult = findIslandAtLocationAndValidate(location);
        if (!islandFindResult.isEmpty()) {
            if (islandFindResult.isLoading()) {
                return IslandActionGuardResult.ISLAND_LOADING;
            }
            if (!playerIsInIsland(entity, islandFindResult)) {
                return IslandActionGuardResult.ACTION_PROHIBITED;
            }
        }
        return IslandActionGuardResult.ACTION_PERMITED;
    }

    private IslandFindResult findIslandAtLocationAndValidate(Location location) {
        IslandFindResult islandFindResult = islandService.findIslandAtLocation(location);
        if (islandFindResult.isLoading()) {
            throw ValidationException.of("Island is loading");
        }
        return islandFindResult;
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

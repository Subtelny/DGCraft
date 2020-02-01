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

    public boolean accessToInteract(Entity entity, Entity toInteract) {
        if (entity.hasPermission("dgcraft.islands.interact.bypass")) {
            return true;
        }
        if (isPlayer(entity)) {
            return true;
        }
        Location location = toInteract.getLocation();
        IslandFindResult islandFindResult = findIslandAtLocationAndValidate(location);

        if (islandFindResult.isEmpty()) {
            return true;
        }

        Optional<Island> islandOpt = getIslandFromResult(islandFindResult);
        if (islandOpt.isPresent()) {
            Player player = (Player) entity;
            Island island = islandOpt.get();
            return islandService.isInIsland(player, island);
        }
        return true;
    }

    public boolean accessToHit(Entity attacker, Entity victim) {
        if (attacker.hasPermission("dgcraft.islands.attack.bypass")) {
            return true;
        }
        if (isPlayer(attacker)) {
            return true;
        }
        Location location = victim.getLocation();
        IslandFindResult islandFindResult = findIslandAtLocationAndValidate(location);
        if (islandFindResult.isEmpty()) {
            return true;
        }
        Optional<Island> islandOpt = getIslandFromResult(islandFindResult);
        if (islandOpt.isPresent()) {
            Player player = (Player) attacker;
            Island island = islandOpt.get();
            return islandService.isInIsland(player, island);
        }
        return true;
    }

    public boolean accessToBuild(Player player, Location location) {
        if (player.hasPermission("dgcraft.islands.build.bypass")) {
            return true;
        }
        IslandFindResult islandFindResult = islandService.findIslandAtLocation(location);
        if (islandFindResult.isEmpty()) {
            return true;
        }
        Optional<Island> islandOpt = getIslandFromResult(islandFindResult);
        if (islandOpt.isPresent()) {
            Island island = islandOpt.get();
            return islandService.isInIsland(player, island);
        }
        return true;
    }

    private boolean isPlayer(Entity entity) {
        return entity.getType() == EntityType.PLAYER;
    }

    private Optional<Island> getIslandFromResult(IslandFindResult result) {
        CompletableFuture<Optional<Island>> future = result.getIsland();
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw ValidationException.of("There is problem with load island");
        }
    }

    private IslandFindResult findIslandAtLocationAndValidate(Location location) {
        IslandFindResult islandFindResult = islandService.findIslandAtLocation(location);
        if (islandFindResult.isLoading()) {
            throw ValidationException.of("Island is loading");
        }
        return islandFindResult;
    }

}

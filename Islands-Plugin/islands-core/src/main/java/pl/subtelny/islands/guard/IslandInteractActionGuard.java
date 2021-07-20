package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.cqrs.query.IslandFindResult;
import pl.subtelny.islands.api.cqrs.query.IslandQueryService;
import pl.subtelny.islands.api.flags.IslandFlag;
import pl.subtelny.islands.api.flags.IslandFlags;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.BlockUtil;

public class IslandInteractActionGuard extends ActionGuard {

    public static final String INTERACT_BYPASS_PERMISSION = "dgcraft.islands.interact.bypass";

    IslandInteractActionGuard(IslandQueryService islandQueryService, IslanderQueryService islanderQueryService) {
        super(islandQueryService, islanderQueryService);
    }

    public IslandActionGuardResult accessToBreed(Entity entity, Location location) {
        if (entity.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        if (entity.getType() != EntityType.PLAYER) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        Player player = (Player) entity;
        return playerHasAccessToBreed(player, location);
    }

    public IslandActionGuardResult accessToMount(Entity entity, Entity mount) {
        if (entity.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        if (entity.getType() != EntityType.PLAYER) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        Player player = (Player) entity;
        return playerHasAccessToMount(player, mount);
    }

    public IslandActionGuardResult accessToInteract(Player player, Block block) {
        if (player.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        if (BlockUtil.isOpenable(block)) {
            return playerHasAccessToInteract(IslandFlags.GUEST_OPEN, player, block.getLocation());
        }
        if (BlockUtil.isSwitchable(block)) {
            return playerHasAccessToInteract(IslandFlags.GUEST_SWITCH, player, block.getLocation());
        }
        if (BlockUtil.isPressurable(block)) {
            return playerHasAccessToInteract(IslandFlags.GUEST_PRESSURE, player, block.getLocation());
        }
        return playerHasAccessToInteract(player, block.getLocation());
    }

    public IslandActionGuardResult accessToInteract(Entity entity, Entity toInteract) {
        if (entity.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        if (entity.getType() == EntityType.PLAYER) {
            return playerHasAccessToInteract((Player) entity, toInteract.getLocation());
        }
        return IslandActionGuardResult.ACTION_PERMITTED;
    }

    public IslandActionGuardResult accessToLeashEntity(Player player, Entity toLeash) {
        if (player.hasPermission(INTERACT_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        IslandFindResult result = islandQueryService.findIsland(toLeash.getLocation());
        if (result.isNotIslandWorld()) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return result.getResult()
                .filter(island -> playerHasAccessToInteract(IslandFlags.GUEST_LEASH_ENTITY, player, island))
                .map(island -> IslandActionGuardResult.ACTION_PERMITTED)
                .orElse(IslandActionGuardResult.ACTION_PROHIBITED);
    }

    private IslandActionGuardResult playerHasAccessToInteract(Player player, Location location) {
        IslandFindResult result = islandQueryService.findIsland(location);
        if (result.isNotIslandWorld()) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return result.getResult()
                .filter(island -> playerHasAccessToInteract(player, island))
                .map(island -> IslandActionGuardResult.ACTION_PERMITTED)
                .orElse(IslandActionGuardResult.ACTION_PROHIBITED);
    }

    private IslandActionGuardResult playerHasAccessToInteract(IslandFlag islandFlag, Player player, Location location) {
        IslandFindResult result = islandQueryService.findIsland(location);
        return result.getResult()
                .filter(island -> playerHasAccessToInteract(islandFlag, player, island))
                .map(island -> IslandActionGuardResult.ACTION_PERMITTED)
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    private IslandActionGuardResult playerHasAccessToBreed(Player player, Location location) {
        return playerHasAccessToInteract(IslandFlags.BREED, player, location);
    }

    private IslandActionGuardResult playerHasAccessToMount(Player player, Entity mount) {
        return playerHasAccessToInteract(IslandFlags.GUEST_MOUNT, player, mount.getLocation());
    }

    private boolean playerHasAccessToInteract(IslandFlag flag, Player player, Island island) {
        return flag.getValue(island.getConfiguration()) || playerHasAccessToInteract(player, island);
    }

    private boolean playerHasAccessToInteract(Player player, Island island) {
        Islander islander = islanderQueryService.getIslander(player);
        return island.isMember(islander);
    }

}

package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.flags.IslandFlags;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;

public class IslandItemActionGuard extends ActionGuard {

    public static final String ITEM_BYPASS_PERMISSION = "dgcraft.islands.item.bypass";

    IslandItemActionGuard(IslandQueryService islandQueryService,
                                 IslanderQueryService islanderQueryService) {
        super(islandQueryService, islanderQueryService);
    }

    public IslandActionGuardResult accessToPickupItem(Entity entity, Location location) {
        if (entity.hasPermission(ITEM_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        IslandFindResult result = islandQueryService.findIsland(location);
        return result.getResult()
                .map(island -> entityHasAccessToPickupItem(entity, island))
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    public IslandActionGuardResult accessToDropItem(Entity entity, Location location) {
        if (entity.hasPermission(ITEM_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        IslandFindResult result = islandQueryService.findIsland(location);
        return result.getResult()
                .map(island -> entityHasAccessToDropItem(entity, island))
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    private IslandActionGuardResult entityHasAccessToPickupItem(Entity entity, Island island) {
        if (EntityType.PLAYER.equals(entity.getType())) {
            Islander islander = islanderQueryService.getIslander((Player) entity);
            if (island.isMember(islander)) {
                return IslandActionGuardResult.ACTION_PERMITTED;
            } else if (IslandFlags.GUEST_PICKUP_ITEM.getValue(island.getConfiguration())) {
                return IslandActionGuardResult.ACTION_PERMITTED;
            }
        }
        return IslandActionGuardResult.ACTION_PROHIBITED;
    }

    private IslandActionGuardResult entityHasAccessToDropItem(Entity entity, Island island) {
        if (EntityType.PLAYER.equals(entity.getType())) {
            Islander islander = islanderQueryService.getIslander((Player) entity);
            if (island.isMember(islander)) {
                return IslandActionGuardResult.ACTION_PERMITTED;
            } else if (IslandFlags.GUEST_DROP_ITEM.getValue(island.getConfiguration())) {
                return IslandActionGuardResult.ACTION_PERMITTED;
            }
        }
        return IslandActionGuardResult.ACTION_PROHIBITED;
    }

}

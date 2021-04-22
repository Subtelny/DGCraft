package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.flags.IslandFlag;
import pl.subtelny.islands.island.flags.IslandFlags;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;

public class IslandEnterActionGuard extends ActionGuard {

    public static final String ENTER_BYPASS_PERMISSION = "dgcraft.islands.enter.bypass";

    IslandEnterActionGuard(IslandQueryService islandQueryService, IslanderQueryService islanderQueryService) {
        super(islandQueryService, islanderQueryService);
    }

    public IslandActionGuardResult accessToFly(Player player, Location location) {
        if (player.hasPermission(ENTER_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return playerHasAccessToFly(player, location);
    }

    public IslandActionGuardResult accessToEnter(Player player, Location location) {
        if (player.hasPermission(ENTER_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return playerHasAccessToEnter(player, location);
    }

    private IslandActionGuardResult playerHasAccessToFly(Player player, Location location) {
        IslandFindResult result = islandQueryService.findIsland(location);
        return result.getResult()
                .filter(island -> playerHasNotAccess(IslandFlags.GUEST_FLY, player, island))
                .map(island -> IslandActionGuardResult.ACTION_PROHIBITED)
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    private IslandActionGuardResult playerHasAccessToEnter(Player player, Location location) {
        IslandFindResult result = islandQueryService.findIsland(location);
        return result.getResult()
                .filter(island -> playerHasNotAccess(IslandFlags.GUEST_ENTER, player, island))
                .map(island -> IslandActionGuardResult.ACTION_PROHIBITED)
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    private boolean playerHasNotAccess(IslandFlag islandFlag, Player player, Island island) {
        return !islandFlag.getValue(island.getConfiguration()) && !playerHasAccess(player, island);
    }

    private boolean playerHasAccess(Player player, Island island) {
        Islander islander = islanderQueryService.getIslander(player);
        return island.isMember(islander);
    }

}

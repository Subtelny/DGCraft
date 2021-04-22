package pl.subtelny.islands.guard;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.flags.IslandFlags;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.entity.EntityTypeUtils;

public class IslandHitActionGuard extends ActionGuard {

    public static final String ATTACK_BYPASS_PERMISSION = "dgcraft.islands.attack.bypass";

    IslandHitActionGuard(IslandQueryService islandQueryService, IslanderQueryService islanderQueryService) {
        super(islandQueryService, islanderQueryService);
    }

    public IslandActionGuardResult accessToHit(Entity attacker, Entity victim) {
        if (attacker.hasPermission(ATTACK_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return entityHasAccessToHit(attacker, victim);
    }

    private IslandActionGuardResult entityHasAccessToHit(Entity attacker, Entity victim) {
        Entity realAttacker = getRealEntity(attacker);
        if (realAttacker.hasPermission(ATTACK_BYPASS_PERMISSION)) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        if (EntityType.PLAYER == realAttacker.getType()) {
            return playerHasAccessToHit((Player) realAttacker, victim);
        }
        return IslandActionGuardResult.ACTION_PERMITTED;
    }

    private IslandActionGuardResult playerHasAccessToHit(Player attacker, Entity victim) {
        if (EntityType.PLAYER == victim.getType()) {
            return playerHasAccessToHit(attacker, (Player) victim);
        }
        IslandFindResult result = islandQueryService.findIsland(victim.getLocation());
        return result.getResult()
                .filter(island -> !playerHasAccessToHit(attacker, island, victim.getType()))
                .map(island -> IslandActionGuardResult.ACTION_PROHIBITED)
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);

    }

    private IslandActionGuardResult playerHasAccessToHit(Player attacker, Player victim) {
        IslandFindResult result = islandQueryService.findIsland(victim.getLocation());
        if (result.isNotIslandWorld()) {
            return IslandActionGuardResult.ACTION_PERMITTED;
        }
        return result.getResult()
                .filter(island -> playerHasAccessToHit(island, attacker))
                .map(island -> IslandActionGuardResult.ACTION_PERMITTED)
                .orElse(IslandActionGuardResult.ACTION_PROHIBITED);
    }


    private boolean playerHasAccessToHit(Island victimIsland, Player attacker) {
        boolean pvpFlag = IslandFlags.PVP.getValue(victimIsland.getConfiguration());
        return pvpFlag && victimIsland.getCuboid().contains(attacker.getLocation());
    }

    private boolean playerHasAccessToHit(Player player, Island island, EntityType entityType) {
        boolean hitFlag = false;
        if (EntityTypeUtils.isPassive(entityType)) {
            hitFlag = IslandFlags.GUEST_ATTACK_PASSIVE_MOBS.getValue(island.getConfiguration());
        } else if (EntityTypeUtils.isAggressive(entityType)) {
            hitFlag = IslandFlags.GUEST_ATTACK_AGGRESSIVE_MOBS.getValue(island.getConfiguration());
        }
        if (hitFlag) {
            return true;
        }
        Islander islander = islanderQueryService.getIslander(player);
        return island.isMember(islander);
    }

    private Entity getRealEntity(Entity entity) {
        if (entity instanceof Projectile) {
            ProjectileSource shooter = ((Projectile) entity).getShooter();
            if (shooter instanceof Entity) {
                return (Entity) shooter;
            }
        }
        return entity;
    }

}

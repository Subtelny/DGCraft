package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.projectiles.ProjectileSource;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;

import java.util.List;

@Component
public class EntityEventListener implements Listener {

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public EntityEventListener(IslandActionGuard islandActionGuard) {
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityPickupItem(EntityPickupItemEvent e) {
        Location location = e.getItem().getLocation();
        LivingEntity entity = e.getEntity();
        IslandActionGuardResult result = islandActionGuard.accessToItem(entity, location);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDropItem(EntityDropItemEvent e) {
        Location location = e.getItemDrop().getLocation();
        Entity entity = e.getEntity();
        IslandActionGuardResult result = islandActionGuard.accessToItem(entity, location);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        Location source = e.getLocation();
        List<Block> blocks = e.blockList();
        IslandActionGuardResult result = islandActionGuard.accessToExplodeAndValidateBlocks(source, blocks);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity damager = getRealDamagerEntity(e.getDamager());
        IslandActionGuardResult result = islandActionGuard.accessToHit(damager, entity);
        if (result.isActionProhibited()) {
            e.setDamage(0);
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityTame(EntityTameEvent e) {
        AnimalTamer owner = e.getOwner();
        if (!(owner instanceof Player)) {
            return;
        }
        Player player = (Player) owner;
        LivingEntity entity = e.getEntity();

        IslandActionGuardResult result = islandActionGuard.accessToInteract(player, entity);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

    private Entity getRealDamagerEntity(Entity entity) {
        if (entity instanceof Projectile) {
            ProjectileSource shooter = ((Projectile) entity).getShooter();
            if (shooter instanceof Entity) {
                return (Entity) shooter;
            }
        }
        return entity;
    }

}

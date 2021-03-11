package pl.subtelny.islands.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;

@Component
public class HangingEventListener implements Listener {

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public HangingEventListener(IslandActionGuard islandActionGuard) {
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(ignoreCancelled = true)
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {
        Entity remover = e.getRemover();
        Hanging target = e.getEntity();

        if (remover != null) {
            Entity realRemoverEntity = getRealRemoverEntity(remover);
            IslandActionGuardResult result = islandActionGuard.accessToInteract(realRemoverEntity, target);
            if (result.isActionProhibited()) {
                e.setCancelled(true);
            }
        }
    }

    private Entity getRealRemoverEntity(Entity entity) {
        if (entity instanceof Projectile) {
            ProjectileSource shooter = ((Projectile) entity).getShooter();
            if (shooter instanceof Entity) {
                return (Entity) shooter;
            }
        }
        return entity;
    }

}

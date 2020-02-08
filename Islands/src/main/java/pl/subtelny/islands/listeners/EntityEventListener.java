package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTameEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.service.IslandActionGuard;
import pl.subtelny.islands.service.IslandActionGuardResult;
import pl.subtelny.islands.service.IslandService;

import java.util.List;

@Component
public class EntityEventListener implements Listener {

    private final IslandService islandService;

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public EntityEventListener(IslandService islandService, IslandActionGuard islandActionGuard) {
        this.islandService = islandService;
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Location source = e.getLocation();
        List<Block> blocks = e.blockList();
        IslandActionGuardResult result = islandActionGuard.accessToExplodeAndValidateBlocks(source, blocks);
        if (IslandActionGuardResult.ACTION_PERMITED != result) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Entity entity = e.getEntity();
        Entity attacker = e.getDamager();
        IslandActionGuardResult result = islandActionGuard.accessToHit(attacker, entity);
        if (IslandActionGuardResult.ACTION_PERMITED != result) {
            e.setDamage(0);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent e) {
        AnimalTamer owner = e.getOwner();
        if (e.isCancelled() || !(owner instanceof Player)) {
            return;
        }
        Player player = (Player) owner;
        LivingEntity entity = e.getEntity();

        IslandActionGuardResult result = islandActionGuard.accessToInteract(player, entity);
        if (IslandActionGuardResult.ACTION_PERMITED != result) {
            e.setCancelled(true);
        }
    }

}

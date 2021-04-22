package pl.subtelny.islands.listeners;

import com.destroystokyo.paper.event.entity.PreSpawnerSpawnEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;

@Component
public class EntitySpawnEventListener implements Listener {

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public EntitySpawnEventListener(IslandActionGuard islandActionGuard) {
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        IslandActionGuardResult result = islandActionGuard.accessToSpawn(e.getEntity(), e.getLocation());
        e.setCancelled(result.isActionProhibited());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPreSpawnerSpawn(PreSpawnerSpawnEvent e) {
        Location spawnerLocation = e.getSpawnerLocation();
        Location spawnLocation = e.getSpawnLocation();

        IslandActionGuardResult result = islandActionGuard.accessToSpawnerSpawn(spawnerLocation, spawnLocation);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
            e.setShouldAbortSpawn(true);
        }
    }

}

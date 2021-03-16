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
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.utilities.configuration.datatype.BooleanDataType;

@Component
public class EntitySpawnEventListener implements Listener {

    private static final String SPAWNERS_DISABLED_KEY = "SPAWNERS_DISABLED";

    private final IslandQueryService islandQueryService;

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public EntitySpawnEventListener(IslandQueryService islandQueryService, IslandActionGuard islandActionGuard) {
        this.islandQueryService = islandQueryService;
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
        IslandFindResult islandFindResult = islandQueryService.findIsland(spawnerLocation);

        boolean cancelEvent = islandFindResult.getResult()
                .map(island -> cancelPreSpawnerSpawnEvent(spawnLocation, island))
                .orElse(false);

        e.setCancelled(cancelEvent);
        e.setShouldAbortSpawn(cancelEvent);
    }

    private boolean cancelPreSpawnerSpawnEvent(Location spawnLoc, Island island) {
        return island.getCuboid().contains(spawnLoc) && isSpawnersDisabled(island);
    }

    private boolean isSpawnersDisabled(Island island) {
        return island.getConfiguration().getValue(SPAWNERS_DISABLED_KEY, BooleanDataType.TYPE);
    }

}

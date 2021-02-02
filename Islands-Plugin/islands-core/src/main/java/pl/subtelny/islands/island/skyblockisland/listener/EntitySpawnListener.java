package pl.subtelny.islands.island.skyblockisland.listener;

import com.destroystokyo.paper.event.entity.PreSpawnerSpawnEvent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.entity.EntityTypeUtils;

@Component
public class EntitySpawnListener implements Listener {

    private static final ConfigurationKey SPAWNERS_DISABLED_KEY = new ConfigurationKey("SPAWNERS_DISABLED");

    private static final ConfigurationKey SPAWN_AGGRESSIVE_MOB_DISABLED_KEY = new ConfigurationKey("SPAWN_AGGRESSIVE_MOB_DISABLED");

    private static final ConfigurationKey SPAWN_PASSIVE_MOB_DISABLED_KEY = new ConfigurationKey("SPAWN_PASSIVE_MOB_DISABLED_KEY");

    private final IslandQueryService islandQueryService;

    @Autowired
    public EntitySpawnListener(IslandQueryService islandQueryService) {
        this.islandQueryService = islandQueryService;
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        IslandFindResult islandFindResult = islandQueryService.findIsland(e.getLocation());
        islandFindResult.getResult().ifPresent(island -> handleCreatureSpawn(e, island));
    }

    @EventHandler
    public void onPreSpawnerSpawn(PreSpawnerSpawnEvent e) {
        Location spawnerLocation = e.getSpawnerLocation();
        IslandFindResult islandFindResult = islandQueryService.findIsland(spawnerLocation);
        islandFindResult.getResult().ifPresent(island -> handlePreSpawnerSpawn(e, island));
    }

    private void handleCreatureSpawn(CreatureSpawnEvent e, Island island) {
        if (isSpawnEntityDisabled(island, e.getEntityType())) {
            e.setCancelled(true);
        }
    }

    private void handlePreSpawnerSpawn(PreSpawnerSpawnEvent e, Island island) {
        boolean cancelEvent = needToCancelPreSpawnerSpawnEvent(island, e.getSpawnLocation());
        e.setCancelled(cancelEvent);
        e.setShouldAbortSpawn(cancelEvent);
    }

    private boolean needToCancelPreSpawnerSpawnEvent(Island island, Location spawnLoc) {
        if (!island.getCuboid().contains(spawnLoc)) {
            return true;
        }
        return isSpawnersDisabled(island);
    }

    private boolean isSpawnEntityDisabled(Island island, EntityType entityType) {
        if (EntityTypeUtils.isAggressive(entityType)) {
            return island.getConfiguration().findValue(SPAWN_AGGRESSIVE_MOB_DISABLED_KEY, Boolean.class).orElse(false);
        }
        if (EntityTypeUtils.isPassive(entityType)) {
            return island.getConfiguration().findValue(SPAWN_PASSIVE_MOB_DISABLED_KEY, Boolean.class).orElse(false);
        }
        return false;
    }

    private boolean isSpawnersDisabled(Island island) {
        return island.getConfiguration().findValue(SPAWNERS_DISABLED_KEY, Boolean.class).orElse(false);
    }

}

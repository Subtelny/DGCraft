package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandConfiguration;
import pl.subtelny.islands.api.cqrs.query.IslandFindResult;
import pl.subtelny.islands.api.cqrs.query.IslandQueryService;
import pl.subtelny.islands.api.flags.IslandFlags;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.utilities.entity.EntityTypeUtils;

public class IslandSpawnActionGuard extends ActionGuard {

    IslandSpawnActionGuard(IslandQueryService islandQueryService, IslanderQueryService islanderQueryService) {
        super(islandQueryService, islanderQueryService);
    }

    public IslandActionGuardResult accessToSpawnerSpawn(Location spawnerLoc, Location spawnLocation) {
        IslandFindResult result = islandQueryService.findIsland(spawnerLoc);
        return result.getResult()
                .filter(island -> !accessToSpawnerSpawn(island, spawnLocation))
                .map(island -> IslandActionGuardResult.ACTION_PROHIBITED)
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    public IslandActionGuardResult accessToSpawn(Entity entity, Location location) {
        IslandFindResult result = islandQueryService.findIsland(location);
        if (!result.isNotIslandWorld() && result.getResult().isEmpty()) {
            return IslandActionGuardResult.ACTION_PROHIBITED;
        }
        return result.getResult()
                .map(island -> entityHasAccessToSpawn(island, entity.getType()))
                .orElse(IslandActionGuardResult.ACTION_PERMITTED);
    }

    private IslandActionGuardResult entityHasAccessToSpawn(Island island, EntityType entityType) {
        IslandConfiguration configuration = island.getConfiguration();
        boolean accessToSpawn = true;
        if (EntityTypeUtils.isAggressive(entityType)) {
            accessToSpawn = IslandFlags.SPAWN_AGGRESSIVE_MOB.getValue(configuration);
        } else if (EntityTypeUtils.isPassive(entityType)) {
            accessToSpawn = IslandFlags.SPAWN_PASSIVE_MOB.getValue(configuration);
        }
        return accessToSpawn ? IslandActionGuardResult.ACTION_PERMITTED : IslandActionGuardResult.ACTION_PROHIBITED;
    }

    private boolean accessToSpawnerSpawn(Island island, Location spawnLocation) {
        boolean spawnerFlag = IslandFlags.MOB_SPAWNERS.getValue(island.getConfiguration());
        boolean spawnMatchIslandCuboid = island.getCuboid().contains(spawnLocation);
        return spawnerFlag && spawnMatchIslandCuboid;
    }

}

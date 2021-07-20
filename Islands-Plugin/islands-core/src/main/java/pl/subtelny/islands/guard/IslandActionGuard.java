package pl.subtelny.islands.guard;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.api.cqrs.query.IslandQueryService;
import pl.subtelny.islands.islander.IslanderQueryService;

import java.util.List;

@Component
public class IslandActionGuard {

    private final IslandBuildActionGuard buildActionGuard;

    private final IslandItemActionGuard itemActionGuard;

    private final IslandSpawnActionGuard spawnActionGuard;

    private final IslandInteractActionGuard interactActionGuard;

    private final IslandHitActionGuard hitActionGuard;

    private final IslandEnterActionGuard enterActionGuard;

    @Autowired
    public IslandActionGuard(IslandQueryService islandQueryService, IslanderQueryService islanderQueryService) {
        this.buildActionGuard = new IslandBuildActionGuard(islandQueryService, islanderQueryService);
        this.itemActionGuard = new IslandItemActionGuard(islandQueryService, islanderQueryService);
        this.spawnActionGuard = new IslandSpawnActionGuard(islandQueryService, islanderQueryService);
        this.interactActionGuard = new IslandInteractActionGuard(islandQueryService, islanderQueryService);
        this.hitActionGuard = new IslandHitActionGuard(islandQueryService, islanderQueryService);
        this.enterActionGuard = new IslandEnterActionGuard(islandQueryService, islanderQueryService);
    }

    public IslandActionGuardResult accessToMultiBuild(Location from, List<Location> blocks) {
        return buildActionGuard.accessToMultiBuild(from, blocks);
    }

    public IslandActionGuardResult accessToSpreadBlock(Location source, Location target) {
        return buildActionGuard.accessToSpreadBlock(source, target);
    }

    public IslandActionGuardResult accessToExplodeAndValidateBlocks(Location source, List<Block> blocks) {
        return buildActionGuard.accessToExplodeAndValidateBlocks(source, blocks);
    }

    public IslandActionGuardResult accessToBuild(Player player, Location location) {
        return buildActionGuard.accessToBuild(player, location);
    }

    public IslandActionGuardResult accessToPickupItem(Entity entity, Location location) {
        return itemActionGuard.accessToPickupItem(entity, location);
    }

    public IslandActionGuardResult accessToDropItem(Entity entity, Location location) {
        return itemActionGuard.accessToDropItem(entity, location);
    }

    public IslandActionGuardResult accessToEnter(Player player, Location location) {
        return enterActionGuard.accessToEnter(player, location);
    }

    public IslandActionGuardResult accessToFly(Player player, Location location) {
        return enterActionGuard.accessToFly(player, location);
    }

    public IslandActionGuardResult accessToSpawnerSpawn(Location spawnerLoc, Location spawnLocation) {
        return spawnActionGuard.accessToSpawnerSpawn(spawnerLoc, spawnLocation);
    }

    public IslandActionGuardResult accessToSpawn(Entity entity, Location location) {
        return spawnActionGuard.accessToSpawn(entity, location);
    }

    public IslandActionGuardResult accessToLeashEntity(Player player, Entity toLeash) {
        return interactActionGuard.accessToLeashEntity(player, toLeash);
    }

    public IslandActionGuardResult accessToInteract(Player player, Block block) {
        return interactActionGuard.accessToInteract(player, block);
    }

    public IslandActionGuardResult accessToInteract(Entity entity, Entity toInteract) {
        return interactActionGuard.accessToInteract(entity, toInteract);
    }

    public IslandActionGuardResult accessToBreed(Entity entity, Location location) {
        return interactActionGuard.accessToBreed(entity, location);
    }

    public IslandActionGuardResult accessToMount(Entity entity, Entity mount) {
        return interactActionGuard.accessToMount(entity, mount);
    }

    public IslandActionGuardResult accessToHit(Entity attacker, Entity victim) {
        return hitActionGuard.accessToHit(attacker, victim);
    }

}

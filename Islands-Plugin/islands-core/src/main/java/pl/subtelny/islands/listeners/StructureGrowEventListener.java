package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandBuildActionGuard;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.query.IslandFindResult;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.List;

@Component
public class StructureGrowEventListener implements Listener {

    private final IslandQueryService islandService;

    @Autowired
    public StructureGrowEventListener(IslandQueryService islandService) {
        this.islandService = islandService;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockFertilize(BlockFertilizeEvent e) {
        Player player = e.getPlayer();
        if (player != null && player.hasPermission(IslandBuildActionGuard.BUILD_BYPASS_PERMISSION)) {
            return;
        }
        Location location = e.getBlock().getLocation();
        IslandFindResult result = islandService.findIsland(location);
        result.getResult().ifPresent(island -> removeBlocksWhenNotInIsland(e.getBlocks(), island));
    }

    @EventHandler(ignoreCancelled = true)
    public void onStructureGrow(StructureGrowEvent e) {
        Player player = e.getPlayer();
        if (player != null && player.hasPermission(IslandBuildActionGuard.BUILD_BYPASS_PERMISSION)) {
            return;
        }
        Location location = e.getLocation();
        IslandFindResult result = islandService.findIsland(location);
        result.getResult().ifPresent(island -> removeBlocksWhenNotInIsland(e.getBlocks(), island));
    }

    private void removeBlocksWhenNotInIsland(List<BlockState> blocks, Island island) {
        Cuboid cuboid = island.getCuboid();
        blocks.stream()
                .filter(blockState -> !cuboid.contains(blockState.getLocation()))
                .forEach(blockState -> blockState.setType(Material.AIR));
    }

}

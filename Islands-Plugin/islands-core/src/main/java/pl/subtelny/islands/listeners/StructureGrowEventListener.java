package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.query.IslandFindResult;
import pl.subtelny.islands.island.query.IslandQueryService;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.List;

@Component
public class StructureGrowEventListener implements Listener {

    private final IslandQueryService islandService;

    @Autowired
    public StructureGrowEventListener(IslandQueryService islandService) {
        this.islandService = islandService;
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent e) {
        if (e.isCancelled()) {
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

package pl.subtelny.islands.listeners;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.repository.IslandFindResult;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.island.IslandsQueryService;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.List;

@Component
public class StructureGrowEventListener implements Listener {

    private final IslandsQueryService islandService;

    @Autowired
    public StructureGrowEventListener(IslandsQueryService islandService) {
        this.islandService = islandService;
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Location location = e.getLocation();
        IslandFindResult result = islandService.findIslandAtLocation(location);
        result.getResult().ifPresent(island -> removeBlocksWhenNotInIsland(e.getBlocks(), island));
    }

    private void removeBlocksWhenNotInIsland(List<BlockState> blocks, AbstractIsland island) {
        Cuboid cuboid = island.getCuboid();
        blocks.stream()
                .filter(blockState -> !cuboid.contains(blockState.getLocation()))
                .forEach(blockState -> blockState.setType(Material.AIR));
    }

}

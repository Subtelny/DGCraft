package pl.subtelny.islands.listeners;

import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.service.IslandService;
import pl.subtelny.utils.cuboid.Cuboid;

@Component
public class StructureGrowEventListener implements Listener {

	private final IslandService islandService;

	@Autowired
	public StructureGrowEventListener(IslandService islandService) {
		this.islandService = islandService;
	}

	@EventHandler
	public void onStructureGrow(StructureGrowEvent e) {
		if(e.isCancelled()) {
			return;
		}
		Location from = e.getLocation();
		Optional<Island> islandOpt = islandService.findIslandAtLocation(from);
		if (islandOpt.isPresent()) {
			Island island = islandOpt.get();
			Cuboid cuboid = island.getCuboid();
			List<BlockState> blocks = e.getBlocks();

			blocks.stream()
					.filter(blockState -> !cuboid.containsLocation(blockState.getLocation()))
					.forEach(blockState -> blockState.setType(Material.AIR));
		}
	}

}

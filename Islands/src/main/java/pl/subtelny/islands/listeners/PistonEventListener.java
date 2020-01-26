package pl.subtelny.islands.listeners;

import java.util.List;
import java.util.Optional;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.service.IslandService;

@Component
public class PistonEventListener implements Listener {

	private final IslandService islandService;

	@Autowired
	public PistonEventListener(IslandService islandService) {
		this.islandService = islandService;
	}

	@EventHandler
	public void onBlockPistonExtend(BlockPistonExtendEvent e) {
		if (e.isCancelled()) {
			return;
		}
		List<Block> blocks = e.getBlocks();
		Block lastBlock;
		if (blocks.size() == 0) {
			lastBlock = e.getBlock().getRelative(e.getDirection());
		} else {
			lastBlock = blocks.get(blocks.size() - 1).getRelative(e.getDirection());
		}

		Optional<Island> islandFromPiston = islandService.findIslandAtLocation(e.getBlock().getLocation());
		Optional<Island> islandAtLastBlock = islandService.findIslandAtLocation(lastBlock.getLocation());

		if (islandFromPiston.isPresent()) {
			if (islandAtLastBlock.isEmpty()) {
				e.setCancelled(true);
				return;
			}
			Island islandPiston = islandFromPiston.get();
			Island islandLastBlock = islandAtLastBlock.get();
			if (!islandPiston.equals(islandLastBlock)) {
				e.setCancelled(true);
			}
		} else if (islandAtLastBlock.isPresent()) {
			e.setCancelled(true);
		}
	}
}

package pl.subtelny.islands.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.service.IslandService;

@Component
public class BlockBreakListener implements Listener {

	private final IslandService islandService;

	@Autowired
	public BlockBreakListener(IslandService islandService) {
		this.islandService = islandService;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		boolean canBuild = islandService.canBuild(player);
		if (!canBuild) {
			e.setCancelled(true);
			e.setDropItems(false);
			e.setExpToDrop(0);
		}
	}

}

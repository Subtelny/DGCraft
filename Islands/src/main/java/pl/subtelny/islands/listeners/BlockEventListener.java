package pl.subtelny.islands.listeners;

import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.service.IslandActionGuard;
import pl.subtelny.islands.service.IslandService;
import pl.subtelny.utils.cuboid.Cuboid;

@Component
public class BlockEventListener implements Listener {

	private final IslandActionGuard islandActionGuard;

	private final IslandService islandService;

	@Autowired
	public BlockEventListener(IslandActionGuard islandActionGuard,
			IslandService islandService) {
		this.islandActionGuard = islandActionGuard;
		this.islandService = islandService;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Player player = e.getPlayer();
		Location location = e.getBlock().getLocation();
		boolean canBuild = islandActionGuard.accessToBuild(player, location);
		if (!canBuild) {
			e.setCancelled(true);
			e.setDropItems(false);
			e.setExpToDrop(0);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Player player = e.getPlayer();
		Location location = e.getBlock().getLocation();
		boolean canBuild = islandActionGuard.accessToBuild(player, location);
		if (!canBuild) {
			e.setCancelled(true);
			e.setBuild(false);
		}
	}

	@EventHandler
	public void onBlockCanBuild(BlockCanBuildEvent e) {
		if (!e.isBuildable()) {
			return;
		}
		Player player = e.getPlayer();
		if (player != null) {
			Location location = e.getBlock().getLocation();
			boolean canBuild = islandActionGuard.accessToBuild(player, location);
			if (!canBuild) {
				e.setBuildable(false);
			}
		}
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Location from = e.getBlock().getLocation();
		Location to = e.getToBlock().getLocation();
		if (locationsNotMatchSameIsland(from, to)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockSpreadEvent(BlockSpreadEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Location source = e.getSource().getLocation();
		Location target = e.getBlock().getLocation();
		if (locationsNotMatchSameIsland(source, target)) {
			e.setCancelled(true);
		}
	}

	private boolean locationsNotMatchSameIsland(Location source, Location target) {
		Optional<Island> islandOpt = islandService.findIslandAtLocation(source);
		if (islandOpt.isPresent()) {
			Island island = islandOpt.get();
			return !island.getCuboid().containsLocation(target);
		}
		return true;
	}

	@EventHandler
	public void onBlockFertilize(BlockFertilizeEvent e) {
		if(e.isCancelled()) {
			return;
		}

		Location location = e.getBlock().getLocation();
		Optional<Island> islandOpt = islandService.findIslandAtLocation(location);
		if (islandOpt.isPresent()) {
			Island island = islandOpt.get();
			if (!island.canBuild(e.getPlayer())) {
				e.setCancelled(true);
				return;
			}
			Cuboid cuboid = island.getCuboid();
			List<BlockState> blocks = e.getBlocks();
			blocks.stream()
					.filter(blockState -> !cuboid.containsLocation(blockState.getLocation()))
					.forEach(blockState -> blockState.setType(Material.AIR));
		}
	}

}

package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.service.IslandActionGuard;
import pl.subtelny.islands.service.IslandActionGuardResult;
import pl.subtelny.islands.service.IslandFindResult;
import pl.subtelny.islands.service.IslandService;

import java.util.Optional;

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
		IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
		if (isAccessToAction(result)) {
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
		IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
		if (isAccessToAction(result)) {
			e.setCancelled(true);
			e.setBuild(false);
		}
	}

	private boolean isAccessToAction(IslandActionGuardResult canBuild) {
		return IslandActionGuardResult.ACTION_PERMITED != canBuild;
	}

	@EventHandler
	public void onBlockCanBuild(BlockCanBuildEvent e) {
		if (!e.isBuildable()) {
			return;
		}
		Player player = e.getPlayer();
		if (player != null) {
			Location location = e.getBlock().getLocation();
			IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
			if (isAccessToAction(result)) {
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
		IslandFindResult result = islandService.findIslandAtLocation(source);
		if(result.isLoading() || result.isEmpty()) {
			return true;
		}

		Optional<Island> islandOpt = result.getIsland().getNow(Optional.empty());
		if(islandOpt.isPresent()) {
			Island island = islandOpt.get();
			return !island.getCuboid().containsLocation(target);
		}
		return true;
	}

}

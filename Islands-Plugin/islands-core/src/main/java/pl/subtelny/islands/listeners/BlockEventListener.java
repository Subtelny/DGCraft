package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlockEventListener implements Listener {

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public BlockEventListener(IslandActionGuard islandActionGuard) {
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockMultiPlace(BlockMultiPlaceEvent e) {
        List<Location> locs = e.getReplacedBlockStates().stream().map(BlockState::getLocation).collect(Collectors.toList());
        IslandActionGuardResult result = islandActionGuard.accessToMultiBuild(e.getBlockAgainst().getLocation(), locs);
        if (result.isActionProhibited()) {
            e.setBuild(false);
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
        if (isAccessRejectedToAction(result)) {
            e.setCancelled(true);
            e.setDropItems(false);
            e.setExpToDrop(0);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlockAgainst().getLocation();
        IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
        if (isAccessRejectedToAction(result)) {
            e.setCancelled(true);
            e.setBuild(false);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockCanBuild(BlockCanBuildEvent e) {
        Player player = e.getPlayer();
        if (player != null) {
            Location location = e.getBlock().getLocation();
            IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
            if (isAccessRejectedToAction(result)) {
                e.setBuildable(false);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent e) {
        Location from = e.getBlock().getLocation();
        Location to = e.getToBlock().getLocation();
        IslandActionGuardResult result = islandActionGuard.accessToSpreadBlock(from, to);
        if (isAccessRejectedToAction(result)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockSpreadEvent(BlockSpreadEvent e) {
        Location source = e.getSource().getLocation();
        Location target = e.getBlock().getLocation();
        IslandActionGuardResult result = islandActionGuard.accessToSpreadBlock(source, target);
        if (isAccessRejectedToAction(result)) {
            e.setCancelled(true);
        }
    }

    private boolean isAccessRejectedToAction(IslandActionGuardResult canBuild) {
        return IslandActionGuardResult.ACTION_PERMITTED != canBuild;
    }

}

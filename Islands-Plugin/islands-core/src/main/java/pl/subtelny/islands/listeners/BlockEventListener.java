package pl.subtelny.islands.listeners;

import pl.subtelny.components.api.Autowired;
import pl.subtelny.components.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

@Component
public class BlockEventListener implements Listener {

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public BlockEventListener(IslandActionGuard islandActionGuard) {
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
        if (isAccessRejectedToAction(result)) {
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
        if (isAccessRejectedToAction(result)) {
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
            IslandActionGuardResult result = islandActionGuard.accessToBuild(player, location);
            if (isAccessRejectedToAction(result)) {
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
        IslandActionGuardResult result = islandActionGuard.accessToSpreadBlock(from, to);
        if (isAccessRejectedToAction(result)) {
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
        IslandActionGuardResult result = islandActionGuard.accessToSpreadBlock(source, target);
        if (isAccessRejectedToAction(result)) {
            e.setCancelled(true);
        }
    }

    private boolean isAccessRejectedToAction(IslandActionGuardResult canBuild) {
        return IslandActionGuardResult.ACTION_PERMITED != canBuild;
    }

}

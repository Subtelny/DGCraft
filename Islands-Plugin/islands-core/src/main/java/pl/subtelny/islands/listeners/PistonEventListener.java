package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PistonEventListener implements Listener {

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public PistonEventListener(IslandActionGuard islandActionGuard) {
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Block lastBlock = getLastBlock(e);
        Location from = e.getBlock().getLocation();
        Location to = lastBlock.getLocation();

        IslandActionGuardResult result = islandActionGuard.accessToSpreadBlock(from, to);
        if (isAccessToActionRejected(result)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent e) {
        Block pistonBlock = e.getBlock();
        List<Block> blocksToMove = e.getBlocks();
        List<Location> locs = blocksToMove.stream().map(Block::getLocation).collect(Collectors.toList());
        IslandActionGuardResult result = islandActionGuard.accessToMultiBuild(pistonBlock.getLocation(), locs);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

    private Block getLastBlock(BlockPistonExtendEvent e) {
        List<Block> blocks = e.getBlocks();
        if (blocks.size() == 0) {
            return e.getBlock().getRelative(e.getDirection());
        } else {
            return blocks.get(blocks.size() - 1).getRelative(e.getDirection());
        }
    }

    private boolean isAccessToActionRejected(IslandActionGuardResult result) {
        return IslandActionGuardResult.ACTION_PERMITTED != result;
    }
}

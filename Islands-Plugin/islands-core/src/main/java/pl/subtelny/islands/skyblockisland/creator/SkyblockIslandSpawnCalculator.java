package pl.subtelny.islands.skyblockisland.creator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import pl.subtelny.islands.Islands;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SkyblockIslandSpawnCalculator {

    private final Cuboid cuboid;

    public SkyblockIslandSpawnCalculator(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public Location calculate() throws InterruptedException, ExecutionException, TimeoutException {
        return Bukkit.getScheduler().callSyncMethod(Islands.plugin, () -> {
            for (Block block : cuboid) {
                if (block.getType() == Material.END_PORTAL_FRAME) {
                    block.setType(Material.AIR);
                    return block.getLocation();
                }
            }
            Location center = cuboid.getCenter();
            Block blockDown = center.getBlock().getRelative(BlockFace.DOWN);
            if (blockDown.getType() == Material.AIR) {
                blockDown.setType(Material.GRASS);
            }
            return center;
        }).get(1, TimeUnit.MINUTES);
    }

}

package pl.subtelny.islands.skyblockisland.creator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.subtelny.islands.Islands;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.cuboid.CuboidUtil;

public class SkyblockIslandSpawnCalculator {

    private final Cuboid cuboid;

    public SkyblockIslandSpawnCalculator(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public Location calculate() {
        var ref = new Object() {
            Location location = cuboid.getCenter();
        };
        JobsProvider.runSync(Islands.plugin, () -> {
            for (Block block : cuboid) {
                if (block.getType() == Material.END_PORTAL_FRAME) {
                    ref.location = block.getLocation();
                    block.setType(Material.AIR);
                    return;
                }
            }
            CuboidUtil.findSafeLocationSpirally(cuboid).ifPresent(location -> ref.location = location);
        });
        return ref.location;
    }

}

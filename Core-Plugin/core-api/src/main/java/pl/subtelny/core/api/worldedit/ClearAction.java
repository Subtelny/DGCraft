package pl.subtelny.core.api.worldedit;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.function.block.BlockReplace;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.visitor.RegionVisitor;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Location;
import org.primesoft.asyncworldedit.api.worldedit.ICancelabeEditSession;

public class ClearAction extends WorldEditAction {

    private final Location pos1;

    private final Location pos2;

    public ClearAction(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    @Override
    protected Operation getOperation(ICancelabeEditSession session) {
        World world = new BukkitWorld(pos1.getWorld());
        BlockVector3 min = BlockVector3.at(pos1.getX(), pos1.getY(), pos1.getZ());
        BlockVector3 max = BlockVector3.at(pos2.getX(), pos2.getY(), pos2.getZ());
        Region region = new CuboidRegion(world, min, max);

        BlockState defaultState = BlockTypes.AIR.getDefaultState();
        BlockReplace replace = new BlockReplace(session, defaultState);
        return new RegionVisitor(region, replace);
    }
}

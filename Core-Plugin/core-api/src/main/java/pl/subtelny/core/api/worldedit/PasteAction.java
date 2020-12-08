package pl.subtelny.core.api.worldedit;

import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.primesoft.asyncworldedit.api.worldedit.ICancelabeEditSession;
import pl.subtelny.utilities.log.LogUtil;

public class PasteAction extends WorldEditAction {

    private final ClipboardHolder holder;

    private final Location location;

    public PasteAction(ClipboardHolder clipboardHolder, Location location) {
        this.holder = clipboardHolder;
        this.location = location;
    }

    @Override
    public Operation getOperation(ICancelabeEditSession session) {
        BlockVector3 to = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return holder.createPaste(session)
                .to(to)
                .ignoreAirBlocks(true)
                .build();
    }

}

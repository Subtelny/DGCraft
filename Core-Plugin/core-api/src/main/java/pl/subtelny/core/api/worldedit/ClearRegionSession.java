package pl.subtelny.core.api.worldedit;

import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;

public class ClearRegionSession extends WorldEditOperationSession {

    private final Location location;

    private final Location location2;

    public ClearRegionSession(Location location, Location location2) {
        this.location = location;
        this.location2 = location2;
    }

    @Override
    public void perform() {
        ClearAction clearAction = new ClearAction(location, location2);
        runOperation(clearAction);
    }

    @Override
    protected World getWorld() {
        return location.getWorld();
    }
}

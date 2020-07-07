package pl.subtelny.core.city;

import org.bukkit.Location;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Optional;

public class CityPortal {

    private final Cuboid cuboid;

    private final Location teleportTarget;

    public CityPortal(Cuboid cuboid, Location teleportTarget) {
        this.cuboid = cuboid;
        this.teleportTarget = teleportTarget;
    }

    public boolean isInPortalCuboid(Location location) {
        return getCuboid().contains(location);
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public Location getTeleportTarget() {
        return teleportTarget;
    }
}

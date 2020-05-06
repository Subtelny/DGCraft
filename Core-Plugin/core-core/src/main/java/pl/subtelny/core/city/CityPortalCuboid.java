package pl.subtelny.core.city;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import pl.subtelny.core.api.city.CityPortal;
import pl.subtelny.utilities.cuboid.Cuboid;

public class CityPortalCuboid implements CityPortal {

    private final Cuboid cuboid;

    private final Location teleportTarget;

    public CityPortalCuboid(Cuboid cuboid, Location teleportTarget) {
        this.cuboid = Preconditions.checkNotNull(cuboid, "Cuboid cannot be null");
        this.teleportTarget = teleportTarget;
    }

    @Override
    public boolean isPortalLocation(Location location) {
        return cuboid.containsLocation(location);
    }

    @Override
    public Location getTeleportTarget() {
        return teleportTarget;
    }

}

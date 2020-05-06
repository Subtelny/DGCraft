package pl.subtelny.core.api.city;

import org.bukkit.Location;

public interface CityPortal {

    boolean isPortalLocation(Location location);

    Location getTeleportTarget();

}

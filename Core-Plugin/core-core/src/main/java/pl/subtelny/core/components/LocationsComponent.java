package pl.subtelny.core.components;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.configuration.Locations;

import java.util.Optional;

@Component
public class LocationsComponent {

    private static final String GLOBAL_SPAWN = "global.spawn";

    private final Locations locations;

    @Autowired
    public LocationsComponent(Locations locations) {
        this.locations = locations;
    }

    public Location getGlobalSpawn() {
        return Optional.ofNullable(locations.get(GLOBAL_SPAWN))
                .orElse(getDefaultWorld().getSpawnLocation());
    }

    public World getDefaultWorld() {
        return Bukkit.getWorlds().get(0);
    }

}

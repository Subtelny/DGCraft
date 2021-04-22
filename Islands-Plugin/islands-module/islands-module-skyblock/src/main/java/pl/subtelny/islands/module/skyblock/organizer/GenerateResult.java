package pl.subtelny.islands.module.skyblock.organizer;

import org.bukkit.Location;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;

public class GenerateResult {

    private final Location spawn;

    private final IslandCoordinates coordinates;

    public GenerateResult(Location spawn, IslandCoordinates coordinates) {
        this.spawn = spawn;
        this.coordinates = coordinates;
    }

    public Location getSpawn() {
        return spawn;
    }

    public IslandCoordinates getCoordinates() {
        return coordinates;
    }
}

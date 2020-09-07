package pl.subtelny.islands.skyblockisland.repository;

import org.bukkit.Location;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.cuboid.Cuboid;
import java.util.Objects;

public class SkyblockIslandCreateRequest {

    private final IslandCoordinates islandCoordinates;

    private final Location spawn;

    private final Islander islander;

    private final Cuboid cuboid;

    public SkyblockIslandCreateRequest(IslandCoordinates islandCoordinates, Location spawn, Islander islander, Cuboid cuboid) {
        this.islandCoordinates = islandCoordinates;
        this.spawn = spawn;
        this.islander = islander;
        this.cuboid = cuboid;
    }

    public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Islander getIslander() {
        return islander;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandCreateRequest that = (SkyblockIslandCreateRequest) o;
        return Objects.equals(islandCoordinates, that.islandCoordinates) &&
                Objects.equals(spawn, that.spawn) &&
                Objects.equals(islander, that.islander) &&
                Objects.equals(cuboid, that.cuboid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandCoordinates, spawn, islander, cuboid);
    }
}

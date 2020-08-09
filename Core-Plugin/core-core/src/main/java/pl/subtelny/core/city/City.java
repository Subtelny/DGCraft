package pl.subtelny.core.city;

import org.bukkit.Location;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Objects;

public class City {

    private final CityId cityId;

    private Location spawn;

    private CityPortal cityPortal;

    private Cuboid cuboid;

    public City(CityId cityId, Location spawn, Cuboid cuboid, CityPortal cityPortal) {
        this.cityId = cityId;
        this.spawn = spawn;
        this.cuboid = cuboid;
        this.cityPortal = cityPortal;
    }

    public CityId getCityId() {
        return cityId;
    }

    public Location getSpawn() {
        return spawn;
    }

    public CityPortal getCityPortal() {
        return cityPortal;
    }

    public boolean isInCityCuboid(Location location) {
        return getCuboid().contains(location);
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public void changeSpawn(Location spawn) {
        this.spawn = spawn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(cityId, city.cityId) &&
                Objects.equals(spawn, city.spawn) &&
                Objects.equals(cityPortal, city.cityPortal) &&
                Objects.equals(cuboid, city.cuboid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, spawn, cityPortal, cuboid);
    }
}

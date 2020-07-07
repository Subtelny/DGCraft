package pl.subtelny.core.city;

import org.bukkit.Location;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Objects;

public class City {

    private final CityType cityType;

    private Location spawn;

    private CityPortal cityPortal;

    private Cuboid cuboid;

    public City(CityType cityType, Location spawn, Cuboid cuboid, CityPortal cityPortal) {
        this.cityType = cityType;
        this.spawn = spawn;
        this.cuboid = cuboid;
        this.cityPortal = cityPortal;
    }

    public CityType getCityType() {
        return cityType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return cityType == city.cityType &&
                Objects.equals(spawn, city.spawn) &&
                Objects.equals(cityPortal, city.cityPortal) &&
                Objects.equals(cuboid, city.cuboid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityType, spawn, cityPortal, cuboid);
    }
}

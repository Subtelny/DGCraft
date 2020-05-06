package pl.subtelny.core.city;

import org.bukkit.Location;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.api.city.City;
import pl.subtelny.core.api.city.CityPortal;

public class CityImpl implements City {

    private final CityType cityType;

    private Location spawn;

    private CityPortal cityPortal;

    public CityImpl(CityPortal cityPortal, Location spawn, CityType cityType) {
        this.cityPortal = cityPortal;
        this.spawn = spawn;
        this.cityType = cityType;
    }

    @Override
    public void changeSpawn(Location spawn) {
        this.spawn = spawn;
    }

    @Override
    public Location getSpawn() {
        return spawn;
    }

    @Override
    public void changeCityPortal(CityPortal cityPortal) {
        this.cityPortal = cityPortal;
    }

    @Override
    public CityPortal getCityPortal() {
        return cityPortal;
    }

    @Override
    public CityType getType() {
        return cityType;
    }

}

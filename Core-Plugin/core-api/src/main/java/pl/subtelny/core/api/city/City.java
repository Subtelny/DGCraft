package pl.subtelny.core.api.city;

import org.bukkit.Location;
import pl.subtelny.core.api.account.CityType;

public interface City {

    void changeSpawn(Location spawn);

    Location getSpawn();

    void changeCityPortal(CityPortal cityPortal);

    CityPortal getCityPortal();

    CityType getType();

}

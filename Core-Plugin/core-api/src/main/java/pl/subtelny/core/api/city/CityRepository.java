package pl.subtelny.core.api.city;

import pl.subtelny.core.api.account.CityType;

import java.util.Set;

public interface CityRepository {

    City get(CityType cityType);

    Set<City> getAll();

    void save(City city);

}

package pl.subtelny.core.city.repository;

import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.city.City;

import java.util.Set;

public interface CityRepository {

    City get(CityType cityType);

    Set<City> getAll();

    void save(City city);

}

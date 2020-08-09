package pl.subtelny.core.city.repository;

import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.City;

import java.util.Optional;
import java.util.Set;

public interface CityRepository {

    Optional<City> find(CityId cityId);

    Set<City> getAll();

    void save(City city);

}

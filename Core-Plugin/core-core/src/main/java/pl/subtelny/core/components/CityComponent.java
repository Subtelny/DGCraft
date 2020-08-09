package pl.subtelny.core.components;

import org.bukkit.Location;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.repository.CityRepository;

import java.util.Optional;

@Component
public class CityComponent {

    private final CityRepository cityRepository;

    @Autowired
    public CityComponent(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Optional<Location> findCityLocation(CityId cityId) {
        return findCity(cityId).map(City::getSpawn);
    }

    public Optional<City> findCity(CityId cityId) {
        return cityRepository.find(cityId);
    }

}

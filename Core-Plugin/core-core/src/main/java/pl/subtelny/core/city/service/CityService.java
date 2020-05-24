package pl.subtelny.core.city.service;

import org.bukkit.Location;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.repository.CityRepository;

@Component
public class CityService {

    private final CityRepository cityRepository;

    private final Accounts accounts;

    @Autowired
    public CityService(CityRepository cityRepository, Accounts accounts) {
        this.cityRepository = cityRepository;
        this.accounts = accounts;
    }

    public Location getCitySpawn(CityType cityType) {
        City city = cityRepository.get(cityType);
        return city.getSpawn();
    }

}

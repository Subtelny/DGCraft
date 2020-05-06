package pl.subtelny.core.city;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.api.city.City;
import pl.subtelny.core.api.city.CityPortal;
import pl.subtelny.core.api.city.CityRepository;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@Component
public class CityService {

    private final CityRepository cityRepository;

    private final Accounts accounts;

    @Autowired
    public CityService(CityRepository cityRepository, Accounts accounts) {
        this.cityRepository = cityRepository;
        this.accounts = accounts;
    }

    public Optional<CityPortal> findPortalAtLocation(Location location) {
        return cityRepository.getAll().stream()
                .filter(city -> city.getCityPortal().isPortalLocation(location))
                .map(City::getCityPortal)
                .findAny();
    }

    public Optional<City> findCity(CityPortal cityPortal) {
        return cityRepository.getAll().stream()
                .filter(city -> city.getCityPortal().equals(cityPortal))
                .findAny();
    }

    public void enterCityPortal(Player player, CityPortal cityPortal) {
        accounts.findAccountAsync(player)
                .whenComplete((accountOpt, throwable) -> {
                    if (accountOpt.isPresent()) {
                        Account account = accountOpt.get();
                        enterCityPortal(player, account, cityPortal);
                    }
                });
    }

    private void enterCityPortal(Player player, Account account, CityPortal cityPortal) {
        City city = findCity(cityPortal)
                .orElseThrow(() -> ValidationException.of("Not found city for portal: " + cityPortal.toString()));

        if (account.getCityType() == null) {
            account.setCityType(city.getType());
            accounts.saveAccount(account);
        }
        if (city.getType().equals(account.getCityType())) {
            player.teleport(cityPortal.getTeleportTarget());
        }
    }
}

package pl.subtelny.core.city.service;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.CityPortal;
import pl.subtelny.core.city.repository.CityRepository;
import pl.subtelny.core.player.CorePlayer;
import pl.subtelny.core.repository.account.AccountRepository;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@Component
public class CityPortalTeleporter {

    private final CityRepository cityRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public CityPortalTeleporter(CityRepository cityRepository, AccountRepository accountRepository) {
        this.cityRepository = cityRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<CityId> findCityByPortalLocation(Location location) {
        return cityRepository.getAll().stream()
                .filter(city -> city.getCityPortal().isInPortalCuboid(location))
                .map(City::getCityId)
                .findAny();
    }

    public void enterPortal(CorePlayer corePlayer, CityId cityId) {
        Optional<City> cityOpt = corePlayer.getCity();
        cityOpt.ifPresentOrElse(
                city -> enterCity(corePlayer, city),
                () -> joinCity(corePlayer, cityId));
    }

    private void enterCity(CorePlayer corePlayer, City city) {
        CityId cityId = city.getCityId();
        Validation.isTrue(city.getCityId().equals(cityId), "city.portal.not_your_portal");
        teleportToCity(corePlayer, city);
    }

    private void joinCity(CorePlayer corePlayer, CityId cityId) {
        City city = cityRepository.find(cityId)
                .orElseThrow(() -> ValidationException.of("city.not_found", cityId.getInternal()));
        updateAccount(corePlayer, cityId);
        teleportToCity(corePlayer, city);
    }

    private void updateAccount(CorePlayer corePlayer, CityId cityId) {
        Account account = corePlayer.getAccount();
        account.setCityId(cityId);
        accountRepository.saveAccount(account);
    }

    private void teleportToCity(CorePlayer corePlayer, City city) {
        Player player = corePlayer.getPlayer();
        CityPortal cityPortal = city.getCityPortal();
        Location citySpawn = cityPortal.getTeleportTarget();
        player.teleport(citySpawn);
    }

}

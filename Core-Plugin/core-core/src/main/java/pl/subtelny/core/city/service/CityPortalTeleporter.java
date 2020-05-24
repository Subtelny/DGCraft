package pl.subtelny.core.city.service;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.repository.CityRepository;
import pl.subtelny.core.configuration.CoreMessages;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
public class CityPortalTeleporter {

    private final CoreMessages messages;

    private final Accounts accounts;

    private final CityRepository cityRepository;

    @Autowired
    public CityPortalTeleporter(CoreMessages messages, Accounts accounts, CityRepository cityRepository) {
        this.messages = messages;
        this.accounts = accounts;
        this.cityRepository = cityRepository;
    }

    public Optional<CityType> findCityByPortalLocation(Location location) {
        return cityRepository.getAll().stream()
                .filter(city -> city.getCityPortal().isInPortalCuboid(location))
                .map(City::getCityType)
                .findAny();
    }

    public void enterPortal(Player player, CityType cityType) {
        accounts.findAccountAsync(player)
                .whenComplete((account, throwable) -> enterPortal(player, account.orElse(null), cityType));
    }

    public void enterPortal(Player player, @Nullable Account account, CityType cityType) {
        if (account == null) {
            accounts.createAccount(player, cityType);
        }
        if (account == null || account.getCityType() == cityType) {
            City city = cityRepository.get(cityType);
            Location targetOpt = city.getCityPortal().getTeleportTarget();
            player.teleport(targetOpt);
            return;
        }
        messages.sendTo(player, "not_yours_city_portal");
    }

}

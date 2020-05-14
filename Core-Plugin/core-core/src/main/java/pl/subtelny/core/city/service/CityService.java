package pl.subtelny.core.city.service;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
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

    public void teleportToSpawn(Player player) {
        accounts.findAccountAsync(player)
                .whenComplete((account, throwable) -> account.ifPresent(value -> teleportToSpawn(player, value)));
    }

    public void teleportToSpawn(Player player, Account account) {
        City city = cityRepository.get(account.getCityType());
        player.teleport(city.getSpawn());
    }

}

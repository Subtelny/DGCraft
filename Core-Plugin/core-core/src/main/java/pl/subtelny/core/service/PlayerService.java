package pl.subtelny.core.service;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.api.city.City;
import pl.subtelny.core.city.CityService;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.utilities.log.Log;

@Component
public class PlayerService {

    private final Settings settings;

    private final CityService cityService;

    private final Accounts accounts;

    @Autowired
    public PlayerService(Settings settings, CityService cityService, Accounts accounts) {
        this.settings = settings;
        this.cityService = cityService;
        this.accounts = accounts;
    }

    public void teleportToSpawn(Player player) {
        accounts.findAccountAsync(player)
                .thenAccept(account -> {
                    if (account.isPresent()) {
                        teleportToCitySpawn(player, account.get());
                    } else {
                        teleportToGlobalSpawn(player);
                    }
                })
                .handle((account, throwable) -> {
                    Log.warning("Exception during teleport " + player.getName() + ": " + throwable.getMessage());
                    teleportToGlobalSpawn(player);
                    return account;
                });
    }

    private void teleportToCitySpawn(Player player, Account account) {
        City city = cityService.getCity(account.getCityType());
        Location citySpawn = city.getSpawn();
        player.teleport(citySpawn);
    }

    private void teleportToGlobalSpawn(Player player) {
        Location globalSpawn = settings.get("global.spawn", Location.class);
        player.teleport(globalSpawn);
    }

}

package pl.subtelny.core.service;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.city.service.CityService;
import pl.subtelny.core.configuration.Locations;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.utilities.log.LogUtil;

@Component
public class PlayerService {

    private final CityService cityService;

    private final Locations locations;

    private final Accounts accounts;

    @Autowired
    public PlayerService(CityService cityService, Locations locations, Accounts accounts) {
        this.cityService = cityService;
        this.locations = locations;
        this.accounts = accounts;
    }

    public void teleportToSpawn(Player player) {
        accounts.findAccountAsync(player)
                .whenComplete((account, throwable) -> {
                    if (account.isPresent()) {
                        teleportToCitySpawn(player, account.get());
                    } else {
                        teleportToGlobalSpawn(player);
                    }
                })
                .handle((account, throwable) -> {
                    LogUtil.warning("Exception during teleport " + player.getName() + ": " + throwable.getMessage());
                    return account;
                });
    }

    public void teleportToCitySpawn(Player player, Account account) {
        Location citySpawn = cityService.getCitySpawn(account.getCityType());
        player.teleport(citySpawn);
    }

    public void teleportToGlobalSpawn(Player player) {
        Location globalSpawn = locations.get("global.spawn");
        player.teleport(globalSpawn);
    }

}

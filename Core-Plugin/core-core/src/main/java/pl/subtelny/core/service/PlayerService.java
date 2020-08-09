package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.city.service.CityService;
import pl.subtelny.core.configuration.Locations;

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
        accounts.findAccount(player).ifPresentOrElse(
                account -> teleportToCitySpawn(player, account),
                () -> teleportToGlobalSpawn(player));
    }

    public void teleportToCitySpawn(Player player, Account account) {
       // Location citySpawn = cityService.getCitySpawn(account.getCityId());
        //player.teleport(citySpawn);
    }

    public void teleportToGlobalSpawn(Player player) {
        //Location globalSpawn = locations.get(Locations.GLOBAL_SPAWN);
        //player.teleport(globalSpawn);
    }

}

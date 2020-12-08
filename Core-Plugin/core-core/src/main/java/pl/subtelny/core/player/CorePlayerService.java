package pl.subtelny.core.player;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.components.CityComponent;
import pl.subtelny.core.components.LocationsComponent;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class CorePlayerService {

    private final CorePlayerStorage storage;

    private final Accounts accounts;

    private final CityComponent cityComponent;

    private final LocationsComponent locationsComponent;

    @Autowired
    public CorePlayerService(Accounts accounts, CityComponent cityComponent, LocationsComponent locationsComponent) {
        this.accounts = accounts;
        this.cityComponent = cityComponent;
        this.locationsComponent = locationsComponent;
        this.storage = new CorePlayerStorage();
    }

    public CorePlayer getCorePlayer(Player player) {
        return storage.getCache(player, this::buildCorePlayer);
    }

    private CorePlayer buildCorePlayer(Player player) {
        Account account = accounts.findAccount(player)
                .orElseThrow(() -> ValidationException.of("account.not_found", player.getDisplayName()));
        return new CorePlayer(player, account, cityComponent, locationsComponent);
    }

    public void invalidatePlayer(Player player) {
        storage.invalidate(player);
    }

}

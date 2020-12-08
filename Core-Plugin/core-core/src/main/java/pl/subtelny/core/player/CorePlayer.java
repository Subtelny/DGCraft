package pl.subtelny.core.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.city.City;
import pl.subtelny.core.components.CityComponent;
import pl.subtelny.core.components.LocationsComponent;

import java.util.Objects;
import java.util.Optional;

public class CorePlayer {

    private final Player player;

    private final Account account;

    private final CityComponent cityComponent;

    private final LocationsComponent locationsComponent;

    public CorePlayer(Player player, Account account, CityComponent cityComponent, LocationsComponent locationsComponent) {
        this.player = player;
        this.account = account;
        this.cityComponent = cityComponent;
        this.locationsComponent = locationsComponent;
    }

    public void respawn() {
        Location respawnLoc = account.getCityId()
                .flatMap(cityComponent::findCityLocation)
                .orElse(locationsComponent.getGlobalSpawn());
        player.teleport(respawnLoc);
    }

    public Optional<City> getCity() {
        return account.getCityId()
                .flatMap(cityComponent::findCity);
    }

    public Account getAccount() {
        return account;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorePlayer that = (CorePlayer) o;
        return Objects.equals(player, that.player) &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, account);
    }
}

package pl.subtelny.core.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.BeanService;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.city.City;
import pl.subtelny.core.components.CityComponent;
import pl.subtelny.core.components.LocationsComponent;

import java.util.Objects;
import java.util.Optional;

public class CorePlayer {

    private final Player player;

    private final Account account;

    private final BeanService beanService;

    public CorePlayer(Player player, Account account, BeanService beanService) {
        this.player = player;
        this.account = account;
        this.beanService = beanService;
    }

    public void respawn() {
        Location respawnLoc = account.getCityId()
                .flatMap(cityId -> beanService.getBean(CityComponent.class).findCityLocation(cityId))
                .orElse(beanService.getBean(LocationsComponent.class).getGlobalSpawn());
        player.teleport(respawnLoc);
    }

    public Optional<City> getCity() {
        return account.getCityId()
                .flatMap(cityId -> beanService.getBean(CityComponent.class).findCity(cityId));
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

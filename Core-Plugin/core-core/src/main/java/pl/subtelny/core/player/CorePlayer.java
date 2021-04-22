package pl.subtelny.core.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.components.LocationsComponent;

import java.util.Objects;

public class CorePlayer {

    private final Player player;

    private final Account account;

    private final LocationsComponent locationsComponent;

    public CorePlayer(Player player, Account account, LocationsComponent locationsComponent) {
        this.player = player;
        this.account = account;
        this.locationsComponent = locationsComponent;
    }

    public void respawn() {
        Location respawnLoc = locationsComponent.getGlobalSpawn();
        player.teleport(respawnLoc);
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

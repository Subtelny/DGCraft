package pl.subtelny.core.api.confirmation;

import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerConfirmable implements Confirmable {

    private final Player player;

    public PlayerConfirmable(Player player) {
        this.player = player;
    }

    @Override
    public boolean canConfirm(Player player) {
        return this.player.equals(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerConfirmable that = (PlayerConfirmable) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }
}

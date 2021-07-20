package pl.subtelny.islands.api.confirmation;

import org.bukkit.entity.Player;
import pl.subtelny.core.api.confirmation.Confirmable;
import pl.subtelny.islands.islander.model.Islander;

import java.util.Objects;

public class IslanderConfirm implements Confirmable {

    private final Islander islander;

    public IslanderConfirm(Islander islander) {
        this.islander = islander;
    }

    @Override
    public boolean canConfirm(Player player) {
        return islander.getIslanderId().getInternal().equals(player.getUniqueId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslanderConfirm that = (IslanderConfirm) o;
        return Objects.equals(islander, that.islander);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islander);
    }
}

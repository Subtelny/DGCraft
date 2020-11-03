package pl.subtelny.core.confirmation;

import org.bukkit.entity.Player;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.utilities.Callback;

import java.util.Objects;

public class Confirmation {

    private final ConfirmContextId contextId;

    private final Player player;

    private final Callback<Boolean> listener;

    public Confirmation(ConfirmContextId contextId, Player player, Callback<Boolean> listener) {
        this.contextId = contextId;
        this.player = player;
        this.listener = listener;
    }

    public boolean canConfirm(Player player) {
        return this.player.equals(player);
    }

    public void notifyListener(boolean result) {
        if (listener != null) {
            listener.done(result);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Confirmation that = (Confirmation) o;
        return Objects.equals(contextId, that.contextId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contextId);
    }
}

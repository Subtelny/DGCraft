package pl.subtelny.core.api.confirmation;

import org.bukkit.entity.Player;
import pl.subtelny.utilities.Callback;

import java.util.Objects;

public final class ConfirmationRequest {

    private final ConfirmContextId confirmContextId;

    private final Player player;

    private final Callback<Boolean> state;

    private final String title;

    private ConfirmationRequest(ConfirmContextId confirmContextId, Player player, Callback<Boolean> state, String title) {
        this.confirmContextId = confirmContextId;
        this.player = player;
        this.state = state;
        this.title = title;
    }

    public static Builder builder(ConfirmContextId confirmContextId, Player player) {
        return new Builder(confirmContextId, player);
    }

    public ConfirmContextId getConfirmContextId() {
        return confirmContextId;
    }

    public Player getPlayer() {
        return player;
    }

    public Callback<Boolean> getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public static final class Builder {

        private final ConfirmContextId confirmContextId;

        private final Player player;

        private Callback<Boolean> state;

        private String title;

        public Builder(ConfirmContextId confirmContextId, Player player) {
            this.confirmContextId = confirmContextId;
            this.player = player;
        }

        public Builder stateListener(Callback<Boolean> state) {
            this.state = state;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public ConfirmationRequest build() {
            return new ConfirmationRequest(confirmContextId, player, state, title);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationRequest that = (ConfirmationRequest) o;
        return Objects.equals(confirmContextId, that.confirmContextId) &&
                Objects.equals(player, that.player) &&
                Objects.equals(state, that.state) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(confirmContextId, player, state, title);
    }
}

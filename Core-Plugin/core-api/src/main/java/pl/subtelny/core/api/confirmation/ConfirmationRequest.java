package pl.subtelny.core.api.confirmation;

import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.messages.Messageable;

import java.util.Objects;

public final class ConfirmationRequest {

    private final String rawConfirmContextId;

    private final Messageable messageable;

    private final Confirmable canConfirm;

    private final Callback<Boolean> state;

    private final String title;

    private ConfirmationRequest(String rawConfirmContextId,
                                Messageable messageable,
                                Confirmable canConfirm,
                                Callback<Boolean> state,
                                String title) {
        this.rawConfirmContextId = rawConfirmContextId;
        this.messageable = messageable;
        this.canConfirm = canConfirm;
        this.state = state;
        this.title = title;
    }

    public static Builder builder(String rawConfirmContextId, Messageable messageable, Confirmable canConfirm) {
        return new Builder(rawConfirmContextId, messageable, canConfirm);
    }

    public Confirmable getCanConfirm() {
        return canConfirm;
    }

    public String getRawConfirmContextId() {
        return rawConfirmContextId;
    }

    public Messageable getMessageable() {
        return messageable;
    }

    public Callback<Boolean> getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public static final class Builder {

        private final String rawConfirmContextId;

        private final Messageable messageable;

        private final Confirmable canConfirm;

        private Callback<Boolean> state;

        private String title;

        public Builder(String rawConfirmContextId, Messageable messageable, Confirmable canConfirm) {
            this.rawConfirmContextId = rawConfirmContextId;
            this.messageable = messageable;
            this.canConfirm = canConfirm;
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
            return new ConfirmationRequest(rawConfirmContextId, messageable, canConfirm, state, title);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationRequest that = (ConfirmationRequest) o;
        return Objects.equals(rawConfirmContextId, that.rawConfirmContextId)
                && Objects.equals(messageable, that.messageable)
                && Objects.equals(canConfirm, that.canConfirm)
                && Objects.equals(state, that.state)
                && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawConfirmContextId, messageable, canConfirm, state, title);
    }
}

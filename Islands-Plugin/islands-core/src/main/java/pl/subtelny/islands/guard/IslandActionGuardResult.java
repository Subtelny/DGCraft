package pl.subtelny.islands.guard;

public enum IslandActionGuardResult {

    ACTION_PERMITTED,
    ACTION_PROHIBITED;

    public boolean isActionPermitted() {
        return ACTION_PERMITTED == this;
    }

    public boolean isActionProhibited() {
        return ACTION_PROHIBITED == this;
    }

}

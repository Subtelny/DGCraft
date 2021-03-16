package pl.subtelny.islands.guard;

public enum IslandActionGuardResult {

    ACTION_PERMITTED,
    ACTION_PROHIBITED,
    NOT_ISLAND_WORLD;

    public boolean isActionPermitted() {
        return ACTION_PERMITTED == this;
    }

    public boolean isActionProhibited() {
        return ACTION_PROHIBITED == this;
    }

    public boolean isNotIslandWorld() {
        return NOT_ISLAND_WORLD == this;
    }

}

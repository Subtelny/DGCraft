package pl.subtelny.islands.guard;

public enum IslandActionGuardResult {

    ACTION_PERMITED,
    ACTION_PROHIBITED,
    NOT_ISLAND_WORLD;

    public boolean isActionPermited() {
        return ACTION_PERMITED == this;
    }

    public boolean isActionProhibited() {
        return ACTION_PROHIBITED == this;
    }

    public boolean isNotIslandWorld() {
        return NOT_ISLAND_WORLD == this;
    }

}

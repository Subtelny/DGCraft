package pl.subtelny.islands.guard;

public enum IslandActionGuardResult {

    ACTION_PERMITED,
    ACTION_PROHIBITED,
    ISLAND_LOADING,
    NOT_ISLAND_WORLD;

    public boolean isActionPermited() {
        return ACTION_PERMITED == this;
    }

    public boolean isActionProhibited() {
        return ACTION_PROHIBITED == this;
    }

    public boolean isIslandLoading() {
        return ISLAND_LOADING == this;
    }

    public boolean isNotIslandWorld() {
        return NOT_ISLAND_WORLD == this;
    }

}

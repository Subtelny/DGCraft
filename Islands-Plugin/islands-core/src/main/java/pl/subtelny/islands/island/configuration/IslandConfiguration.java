package pl.subtelny.islands.island.configuration;

public class IslandConfiguration {

    private final String worldName;

    private final boolean createEnabled;

    public IslandConfiguration(String worldName, boolean createEnabled) {
        this.worldName = worldName;
        this.createEnabled = createEnabled;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean isCreateEnabled() {
        return createEnabled;
    }
}

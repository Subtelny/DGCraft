package pl.subtelny.islands.island.configuration;

public class IslandConfiguration {

    private final String worldName;

    public IslandConfiguration(String worldName) {
        this.worldName = worldName;
    }

    public String getWorldName() {
        return worldName;
    }
}

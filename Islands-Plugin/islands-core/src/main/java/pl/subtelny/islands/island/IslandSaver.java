package pl.subtelny.islands.island;

public interface IslandSaver<T extends Island> {

    void saveIsland(T island);

}

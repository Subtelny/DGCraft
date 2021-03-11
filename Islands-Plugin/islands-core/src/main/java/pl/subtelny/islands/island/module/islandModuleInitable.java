package pl.subtelny.islands.island.module;

import pl.subtelny.islands.island.Island;

public interface islandModuleInitable<T extends Island> extends IslandModule<T> {

    void initialize();

}

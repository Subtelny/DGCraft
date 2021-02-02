package pl.subtelny.islands.island.module;

import pl.subtelny.islands.island.Island;

public interface IslandModuleInitable<T extends Island> extends IslandModule<T> {

    void initialize();

}

package pl.subtelny.islands.module;

import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.module.IslandModule;

public interface InitiableIslandModule<T extends Island> extends IslandModule<T> {

    void initialize();

}

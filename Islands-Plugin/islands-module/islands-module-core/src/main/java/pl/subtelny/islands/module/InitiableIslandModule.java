package pl.subtelny.islands.module;

import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.module.IslandModule;

public interface InitiableIslandModule<T extends Island> extends IslandModule<T> {

    void initialize();

}

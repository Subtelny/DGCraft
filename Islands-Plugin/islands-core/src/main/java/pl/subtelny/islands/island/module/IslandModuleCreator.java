package pl.subtelny.islands.island.module;

import pl.subtelny.islands.island.Island;

public interface IslandModuleCreator<T extends Island> {

    IslandModule<T> createModule();

}

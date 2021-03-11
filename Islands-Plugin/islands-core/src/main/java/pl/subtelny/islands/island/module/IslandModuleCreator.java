package pl.subtelny.islands.island.module;

import pl.subtelny.islands.island.Island;

import java.io.File;

public interface IslandModuleCreator<T extends Island> {

    islandModuleInitable<T> createModule(File moduleDir);

    String getModuleType();

}

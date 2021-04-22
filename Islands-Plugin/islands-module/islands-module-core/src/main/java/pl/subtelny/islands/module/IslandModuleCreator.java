package pl.subtelny.islands.module;

import pl.subtelny.islands.island.Island;

import java.io.File;

public interface IslandModuleCreator<T extends Island> {

    InitiableIslandModule<T> createModule(File moduleDir);

    String getModuleType();

}

package pl.subtelny.islands.module;

import pl.subtelny.islands.api.Island;

import java.io.File;

public interface IslandModuleCreator<T extends Island> {

    InitiableIslandModule<T> createModule(File moduleDir);

    String getModuleType();

}

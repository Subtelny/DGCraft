package pl.subtelny.islands.island.module;

import java.io.File;

public class IslandModuleConfiguration {

    private final String worldName;

    private final boolean createEnabled;

    private final File moduleDir;

    public IslandModuleConfiguration(String worldName, boolean createEnabled, File moduleDir) {
        this.worldName = worldName;
        this.createEnabled = createEnabled;
        this.moduleDir = moduleDir;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean isCreateEnabled() {
        return createEnabled;
    }

    public File getModuleDir() {
        return moduleDir;
    }
}

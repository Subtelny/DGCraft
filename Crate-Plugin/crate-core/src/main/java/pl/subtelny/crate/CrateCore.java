package pl.subtelny.crate;

import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

public class CrateCore extends ComponentPlugin {

    @Override
    public void onInitialize(ComponentProvider componentProvider) {
        initializeCratesFromDir(componentProvider);
    }

    private void initializeCratesFromDir(ComponentProvider componentProvider) {
        CrateInitializer crateInitializer = componentProvider.getComponent(CrateInitializer.class);
        File cratesDir = FileUtil.getFile(this, "crates");
        if (!cratesDir.exists()) {
            FileUtil.copyFile(this, "crates/example.yml");
        }
        crateInitializer.initializeFromDir(cratesDir);
    }

}

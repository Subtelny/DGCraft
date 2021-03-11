package pl.subtelny.crate;

import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

public class CrateCore extends ComponentPlugin {

    public static CrateCore PLUGIN;

    @Override
    public void onEnable() {
        PLUGIN = this;
    }

    @Override
    public void onInitialize(ComponentProvider componentProvider) {
        initializeMessages();
        initializeCratesFromDir(componentProvider);
    }

    private void initializeMessages() {
        CrateMessages.get().initMessages(PLUGIN);
    }

    private void initializeCratesFromDir(ComponentProvider componentProvider) {
        CrateInitializer crateInitializer = componentProvider.getComponent(CrateInitializer.class);
        File cratesDir = FileUtil.getFile(this, "crates");
        if (!cratesDir.exists()) {
            FileUtil.copyFile(this, "crates/example.yml");
        }
        crateInitializer.initializeFromDir(cratesDir, PLUGIN, null);
    }

}

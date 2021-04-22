package pl.subtelny.crate;

import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.crate.messages.CrateMessages;

public class CrateCore extends ComponentPlugin {

    public static CrateCore PLUGIN;

    public CrateCore() {
        PLUGIN = this;
    }

    @Override
    public void onInitialize(ComponentProvider componentProvider) {
        componentProvider.getComponent(CrateMessages.class)
                .initMessages(this);

        componentProvider.getComponent(CrateInitializer.class)
                .initializeCratePrototypes();
    }

}

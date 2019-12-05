package pl.subtelny.core;

import pl.subtelny.plugin.DGPlugin;
import pl.subtelny.core.configuration.Settings;

public class Core extends DGPlugin {

    @Override
    public void onLoaded() {
        new Settings(this);
    }

    @Override
    public void onEnabled() {
    }

}

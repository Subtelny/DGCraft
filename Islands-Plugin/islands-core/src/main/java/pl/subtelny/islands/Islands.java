package pl.subtelny.islands;

import pl.subtelny.components.core.api.plugin.DGPlugin;
import pl.subtelny.islands.configuration.IslandsConfiguration;

public class Islands extends DGPlugin {

    public static Islands plugin;

    @Override
    public void onEnable() {
        plugin = this;
        onEnabled();
    }

    @Override
    public void onEnabled() {
        IslandsConfiguration.init(this);
    }

    @Override
    public void onInitialize() {

    }
}

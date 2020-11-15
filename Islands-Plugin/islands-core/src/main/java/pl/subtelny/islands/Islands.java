package pl.subtelny.islands;

import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.islands.configuration.IslandsConfiguration;

public class Islands extends ComponentPlugin {

    public static Islands plugin;

    @Override
    public void onEnable() {
        plugin = this;
        IslandsConfiguration.init(this);
    }

    @Override
    public void onInitialize() {

    }
}

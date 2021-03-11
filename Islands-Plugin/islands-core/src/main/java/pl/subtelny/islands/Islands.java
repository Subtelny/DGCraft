package pl.subtelny.islands;

import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.islands.configuration.IslandsConfiguration;

public class Islands extends ComponentPlugin {

    public static Islands PLUGIN;

    @Override
    public void onEnable() {
        PLUGIN = this;
        IslandsConfiguration.init(this);
    }

}

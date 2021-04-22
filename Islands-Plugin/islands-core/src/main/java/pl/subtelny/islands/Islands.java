package pl.subtelny.islands;

import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.message.IslandMessages;

public class Islands extends ComponentPlugin {

    public static Islands PLUGIN;

    @Override
    public void onEnable() {
        PLUGIN = this;
        IslandsConfiguration.init(this);
    }

    @Override
    public void onInitialize(ComponentProvider componentProvider) {
        componentProvider.getComponent(IslandMessages.class)
                .initMessages(this);
    }
}

package pl.subtelny.core;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.ComponentContext;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;

public class Core extends ComponentPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ComponentContext.initialize(this);
    }

}

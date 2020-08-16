package pl.subtelny.core;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.ComponentsContext;
import pl.subtelny.components.core.api.plugin.DGPlugin;

public class Core extends DGPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        onEnabled();
    }

    @Override
    public void onLoad() {
        ComponentsContext.loadBeans();
    }

    @Override
    public void onEnabled() {
        ComponentsContext.loadDependencies(this);
    }

    @Override
    public void onInitialize() {

    }


}

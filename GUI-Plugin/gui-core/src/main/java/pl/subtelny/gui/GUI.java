package pl.subtelny.gui;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;

public class GUI extends ComponentPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
    }

    @Override
    public void onInitialize() {
    }

}

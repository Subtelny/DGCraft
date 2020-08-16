package pl.subtelny.gui;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.plugin.DGPlugin;

public class GUI extends DGPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        onEnabled();
    }

    @Override
    public void onInitialize() {
    }

}

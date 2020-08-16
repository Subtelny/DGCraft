package pl.subtelny.islands;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.plugin.DGPlugin;

public class Islands extends DGPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        onEnabled();
    }

    @Override
    public void onEnabled() {
    }

    @Override
    public void onInitialize() {

    }
}

package pl.subtelny.islands;

import org.bukkit.plugin.Plugin;
import pl.subtelny.core.api.plugin.DGPlugin;

import java.io.File;

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

package pl.subtelny.core.api.plugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public abstract class DGPlugin extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        onEnabled();
    }

    public void onEnabled() {}

    public abstract void onInitialize();

    public List<String> componentsPaths() {
        return Collections.singletonList(getClass().getPackageName());
    }

}

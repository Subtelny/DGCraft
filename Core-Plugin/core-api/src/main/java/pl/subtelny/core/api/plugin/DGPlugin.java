package pl.subtelny.core.api.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import pl.subtelny.components.core.api.PluginData;

import java.util.Collections;
import java.util.List;

public abstract class DGPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        onEnabled();
    }

    public void onEnabled() {}

    public abstract void onInitialize();

    public PluginData getPluginInformation() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> paths = Collections.singletonList(getClass().getPackageName());
        return new PluginData(paths, classLoader);
    }

}

package pl.subtelny.core.api.plugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.util.ClasspathHelper;
import pl.subtelny.components.core.api.PluginInformation;

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

    public PluginInformation getPluginInformation() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> paths = Collections.singletonList(getClass().getPackageName());
        return new PluginInformation(paths, classLoader);
    }

}

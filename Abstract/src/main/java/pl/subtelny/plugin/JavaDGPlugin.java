package pl.subtelny.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.subtelny.core.api.CoreAPI;
import pl.subtelny.core.api.plugin.DGPlugin;

public abstract class JavaDGPlugin extends JavaPlugin implements DGPlugin {

    @Override
    public void onLoad() {
        checkCorePlugin();
    }

    private void checkCorePlugin() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin plugin = pluginManager.getPlugin(CoreAPI.PLUGIN_NAME);
        if (plugin == null) {
            throw new IllegalStateException("Not found Core plugin.");
        }
    }

}

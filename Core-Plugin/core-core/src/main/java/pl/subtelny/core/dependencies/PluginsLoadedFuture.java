package pl.subtelny.core.dependencies;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PluginsLoadedFuture {

    private final Plugin plugin;

    private final Set<Plugin> waitForPlugins;

    public PluginsLoadedFuture(Plugin plugin, Set<Plugin> waitForPlugins) {
        this.plugin = plugin;
        this.waitForPlugins = waitForPlugins;
    }

    public CompletableFuture<Void> waitForAllPlugins() {
        if(waitForPlugins.size() == 0 || waitForPlugins.stream().allMatch(Plugin::isEnabled)) {
            return CompletableFuture.completedFuture(null);
        }
        PluginManager pluginManager = Bukkit.getPluginManager();
        PluginEventListener listener = new PluginEventListener(waitForPlugins);
        pluginManager.registerEvents(listener, plugin);
        return listener.allPluginsLoaded()
                .thenAccept(value -> HandlerList.unregisterAll(listener));
    }

}

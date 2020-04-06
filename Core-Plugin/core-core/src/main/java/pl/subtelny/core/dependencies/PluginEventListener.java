package pl.subtelny.core.dependencies;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

class PluginEventListener implements Listener {

    private final Set<Plugin> waitForPlugins;

    private final CompletableFuture<Boolean> future = new CompletableFuture<>();

    PluginEventListener(Set<Plugin> waitForPlugins) {
        this.waitForPlugins = waitForPlugins;
    }

    @EventHandler
    public void onPluginEnabled(PluginEnableEvent e) {
        if (waitForPlugins.size() == 0) {
            return;
        }
        Plugin plugin = e.getPlugin();
        boolean remove = waitForPlugins.remove(plugin);
        if (remove && waitForPlugins.size() == 0) {
            done();
        }
    }

    private void done() {
        future.complete(true);
    }

    public CompletableFuture<Boolean> allPluginsLoaded() {
        return future;
    }

}

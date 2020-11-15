package pl.subtelny.components.core.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ComponentPluginsLoader {

    private final Plugin plugin;

    public ComponentPluginsLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public CompletableFuture<Void> waitForAllComponentPlugins() {
        List<Plugin> notEnabledPlugins = getComponentPlugins().stream()
                .filter(plugin -> !plugin.isEnabled())
                .collect(Collectors.toList());

        CountDownLatch latch = new CountDownLatch(1);
        PluginEnableListener listener = new PluginEnableListener(latch, notEnabledPlugins);
        Bukkit.getPluginManager().registerEvents(listener, plugin);

        return CompletableFuture.runAsync(() -> {
            try {
                latch.await(1, TimeUnit.MINUTES);
                HandlerList.unregisterAll(listener);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private List<ComponentPlugin> getComponentPlugins() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(plugin -> plugin instanceof ComponentPlugin)
                .map(plugin -> (ComponentPlugin) plugin)
                .collect(Collectors.toList());
    }

    private static class PluginEnableListener implements Listener {

        private final CountDownLatch latch;

        private final List<Plugin> waitingForPlugins;

        private PluginEnableListener(CountDownLatch latch, List<Plugin> waitingForPlugins) {
            this.latch = latch;
            this.waitingForPlugins = waitingForPlugins;
        }

        @EventHandler
        public void pluginEnable(PluginEnableEvent enableEvent) {
            Plugin plugin = enableEvent.getPlugin();
            waitingForPlugins.remove(plugin);

            if (waitingForPlugins.size() == 0) {
                latch.countDown();
            }
        }

    }

}

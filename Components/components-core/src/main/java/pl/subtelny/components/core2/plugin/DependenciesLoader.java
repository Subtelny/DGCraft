package pl.subtelny.components.core2.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.CommandsInitializer;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core2.ComponentException;
import pl.subtelny.components.core2.ComponentPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DependenciesLoader {

    private final Plugin plugin;

    private final List<DependencyActivator> dependencyActivators;

    private final List<Listener> listeners;

    private final List<BaseCommand> commands;

    public DependenciesLoader(Plugin plugin, List<DependencyActivator> dependencyActivators, List<Listener> listeners, List<BaseCommand> commands) {
        this.plugin = plugin;
        this.dependencyActivators = dependencyActivators;
        this.listeners = listeners;
        this.commands = commands;
    }

    public CompletableFuture<DependenciesLoadResults> load() {
        return waitForAllComponentPlugins().thenApply(unused -> {
            int listeners = loadListeners();
            int commands = loadCommands();
            int dependencyActivators = loadDependencyActivators();
            int componentPlugins = informComponentPlugins();
            return new DependenciesLoadResults(commands, listeners, dependencyActivators, componentPlugins);
        });
    }

    private int informComponentPlugins() {
        List<ComponentPlugin> componentPlugins = getComponentPlugins();
        componentPlugins.forEach(ComponentPlugin::onInitialize);
        return componentPlugins.size();
    }

    private int loadDependencyActivators() {
        DependencyActivatorComparator dependencyActivatorComparator = new DependencyActivatorComparator();
        dependencyActivators.stream()
                .sorted(dependencyActivatorComparator)
                .forEach(DependencyActivator::activate);
        return dependencyActivators.size();
    }

    private int loadCommands() {
        CommandsInitializer commandsInitializer = new CommandsInitializer(plugin, commands);
        commandsInitializer.registerCommands();
        return commands.size();
    }

    private int loadListeners() {
        listeners.forEach(listener -> {
            ComponentPlugin pluginForComponent = findPluginForComponent(listener)
                    .orElseThrow(() -> ComponentException.of("Not found plugin for listener " + listener.getClass().getName()));
            Bukkit.getPluginManager().registerEvents(listener, pluginForComponent);
        });
        return listeners.size();
    }

    private CompletableFuture<Void> waitForAllComponentPlugins() {
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

    private Optional<ComponentPlugin> findPluginForComponent(Object component) {
        String packageName = component.getClass().getPackageName();
        return getComponentPlugins().stream()
                .filter(componentPlugin -> packageName.startsWith(componentPlugin.getClass().getPackageName()))
                .findAny();
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

package pl.subtelny.core.dependencies;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.core.CommandsInitializer;
import pl.subtelny.components.core.api.BeanService;
import pl.subtelny.core.api.plugin.DGPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DependenciesService {

    private static final Logger logger = Bukkit.getLogger();

    private final Plugin plugin;

    private final BeanService beanService;

    public DependenciesService(Plugin plugin, BeanService beanService) {
        this.plugin = plugin;
        this.beanService = beanService;
    }

    public void registerPluginsComponents() {
        final Set<DGPlugin> dgPlugins = getDependencyPlugins();
        waitForPlugins(dgPlugins)
                .thenAccept(aVoid -> registerCommands())
                .thenAccept(aVoid -> registerListeners())
                .thenAccept(aVoid -> informDependencyPlugins(dgPlugins))
                .handle((aVoid, throwable) -> {
                    throwable.printStackTrace();
                    return throwable;
                });
    }

    private Set<DGPlugin> getDependencyPlugins() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        return Arrays.stream(pluginManager.getPlugins())
                .filter(plugin -> plugin instanceof DGPlugin)
                .map(plugin -> ((DGPlugin) plugin))
                .collect(Collectors.toSet());
    }

    private CompletableFuture<Void> waitForPlugins(Set<DGPlugin> dgPlugins) {
        Set<Plugin> plugins = dgPlugins.stream()
                .map(plugin -> (Plugin) plugin)
                .collect(Collectors.toSet());
        PluginsLoadedFuture pluginsLoadedFuture = new PluginsLoadedFuture(plugin, plugins);
        return pluginsLoadedFuture.waitForAllPlugins();
    }

    private void registerListeners() {
        List<Listener> listeners = beanService.getBeans(Listener.class);
        PluginManager pluginManager = Bukkit.getPluginManager();
        listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
        logger.info(String.format("Loaded %s listeners", listeners.size()));
    }

    private void registerCommands() {
        List<BaseCommand> commands = beanService.getBeans(BaseCommand.class);
        CommandsInitializer commandsInitializer = new CommandsInitializer(plugin, commands);
        commandsInitializer.registerCommands();
        logger.info(String.format("Loaded %s commands", commands.size()));
    }

    private void informDependencyPlugins(Set<DGPlugin> plugins) {
        plugins.forEach(DGPlugin::onInitialize);
    }

}

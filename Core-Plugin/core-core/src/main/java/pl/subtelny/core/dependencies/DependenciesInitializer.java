package pl.subtelny.core.dependencies;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.components.core.api.BeanService;
import pl.subtelny.components.core.api.DependencyInitialized;
import pl.subtelny.core.api.plugin.DGPlugin;
import pl.subtelny.commands.api.CommandsInitializer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DependenciesInitializer {

    private static final Logger logger = Bukkit.getLogger();

    private final Plugin plugin;

    private final BeanService beanService;

    public DependenciesInitializer(Plugin plugin, BeanService beanService) {
        this.plugin = plugin;
        this.beanService = beanService;
    }

    public void registerPluginsComponents() {
        final Set<DGPlugin> dgPlugins = getDependencyPlugins();
        waitForPlugins(dgPlugins)
                .thenAccept(aVoid -> registerCommands())
                .thenAccept(aVoid -> registerListeners())
                .thenAccept(aVoid -> informBeans())
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
        listeners.forEach(listener -> {
            DGPlugin pluginForBean = findPluginForBean(listener);
            pluginManager.registerEvents(listener, pluginForBean);
        });
        logger.info(String.format("Loaded %s listeners", listeners.size()));
    }

    private void registerCommands() {
        List<BaseCommand> commands = beanService.getBeans(BaseCommand.class);
        CommandsInitializer commandsInitializer = new CommandsInitializer(plugin, commands);
        commandsInitializer.registerCommands();
        logger.info(String.format("Loaded %s commands", commands.size()));
    }

    private void informBeans() {
        List<DependencyInitialized> beans = beanService.getBeans(DependencyInitialized.class);
        beans.forEach(beansInitialized -> {
            DGPlugin pluginForBean = findPluginForBean(beansInitialized);
            beansInitialized.dependencyInitialized(pluginForBean);
        });
    }

    private DGPlugin findPluginForBean(Object bean) {
        String packageName = bean.getClass().getPackageName();
        Optional<DGPlugin> any = getDependencyPlugins().stream()
                .filter(plugin -> plugin.componentsPaths().stream().anyMatch(packageName::startsWith))
                .findAny();
        return any.orElse(null);
    }

    private void informDependencyPlugins(Set<DGPlugin> plugins) {
        plugins.forEach(DGPlugin::onInitialize);
    }

}

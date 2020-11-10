package pl.subtelny.components.core.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.CommandsInitializer;
import pl.subtelny.components.core.BeanStorage;
import pl.subtelny.components.core.ComponentsContext;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.components.core.api.plugin.DGPlugin;

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

    public DependenciesInitializer(Plugin plugin) {
        this.plugin = plugin;
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
        List<Listener> listeners = ComponentsContext.getBeans(Listener.class);
        PluginManager pluginManager = Bukkit.getPluginManager();
        listeners.forEach(listener -> {
            DGPlugin pluginForBean = findPluginForBean(listener);
            pluginManager.registerEvents(listener, pluginForBean);
        });
        logger.info(String.format("Loaded %s listeners", listeners.size()));
    }

    private void registerCommands() {
        List<BaseCommand> commands = ComponentsContext.getBeans(BaseCommand.class);
        CommandsInitializer commandsInitializer = new CommandsInitializer(plugin, commands);
        commandsInitializer.registerCommands();
        logger.info(String.format("Loaded %s commands", commands.size()));
    }

    private void informBeans() {
        List<DependencyActivator> beans = ComponentsContext.getBeans(DependencyActivator.class);
        beans.stream()
                .sorted((t1, t2) -> {
                    int t1Priority = findPriority(t1).getPriority();
                    int t2Priority = findPriority(t2).getPriority();
                    return Integer.compare(t1Priority, t2Priority);
                })
                .forEach(DependencyActivator::activate);
    }

    private DependencyActivatorPriority.Priority findPriority(DependencyActivator dependencyActivator) {
        Class<? extends DependencyActivator> aClass = dependencyActivator.getClass();
        if (aClass.isAnnotationPresent(DependencyActivatorPriority.class)) {
            return aClass.getAnnotation(DependencyActivatorPriority.class).priority();
        }
        return Arrays.stream(aClass.getInterfaces())
                .filter(aClass1 -> aClass1.isAnnotationPresent(DependencyActivatorPriority.class))
                .map(aClass1 -> aClass1.getAnnotation(DependencyActivatorPriority.class).priority())
                .findAny()
                .orElse(DependencyActivatorPriority.Priority.MEDIUM);
    }

    private DGPlugin findPluginForBean(Object bean) {
        String packageName = bean.getClass().getPackageName();
        Optional<DGPlugin> any = getDependencyPlugins().stream()
                .filter(plugin -> plugin.getPluginInformation().getPaths().stream().anyMatch(packageName::startsWith))
                .findAny();
        return any.orElse(null);
    }

    private void informDependencyPlugins(Set<DGPlugin> plugins) {
        plugins.forEach(DGPlugin::onInitialize);
    }

}

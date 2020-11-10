package pl.subtelny.components.core2;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core2.plugin.DependenciesLoadResults;
import pl.subtelny.components.core2.plugin.DependenciesLoader;
import pl.subtelny.utilities.log.LogUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentContext {

    private static final String PATH_TO_SCAN = "pl.subtelny";

    private static ComponentContext context;

    private ComponentsStorage componentsStorage;

    public static void initialize(Plugin plugin) {
        if (context != null) {
            throw new IllegalStateException("Component are initialized already");
        }
        context = new ComponentContext();
        context.init(plugin);
    }

    private void init(Plugin plugin) {
        List<ClassLoader> classLoaders = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(componentPlugin -> componentPlugin instanceof ComponentPlugin)
                .map(componentPlugin -> plugin.getClass().getClassLoader())
                .collect(Collectors.toList());

        ComponentsLoader loader = new ComponentsLoader(classLoaders, PATH_TO_SCAN);
        Map<Class, Object> components = loader.loadComponents();
        this.componentsStorage = new ComponentsStorage(components);
        loadDependencies(plugin);
    }

    private void loadDependencies(Plugin plugin) {
        List<DependencyActivator> activators = componentsStorage.getComponents(DependencyActivator.class);
        List<Listener> listeners = componentsStorage.getComponents(Listener.class);
        List<BaseCommand> commands = componentsStorage.getComponents(BaseCommand.class);
        DependenciesLoader loader = new DependenciesLoader(plugin, activators, listeners, commands);
        loader.load().whenComplete((dependenciesLoadResults, throwable) -> showStatistics(dependenciesLoadResults));
    }

    private void showStatistics(DependenciesLoadResults results) {
        LogUtil.info("================");
        LogUtil.info("  LOADED:");
        LogUtil.info("    - Commands: " + results.getCommands());
        LogUtil.info("    - Listeners: " + results.getListeners());
        LogUtil.info("    - Component Plugins: " + results.getComponentPlugins());
        LogUtil.info("    - Dependency Activators: " + results.getDependencyActivators());
        LogUtil.info("================");
    }

    public ComponentsStorage getComponentsStorage() {
        return componentsStorage;
    }
}

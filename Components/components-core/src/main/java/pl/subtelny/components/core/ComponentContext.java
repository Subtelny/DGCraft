package pl.subtelny.components.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.ComponentException;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.components.core.loader.ComponentBukkitDependenciesLoader;
import pl.subtelny.components.core.loader.ComponentLoader;
import pl.subtelny.components.core.loader.ComponentObjectInfo;
import pl.subtelny.components.core.loader.ComponentsLoader;
import pl.subtelny.components.core.plugin.ComponentPluginsLoader;
import pl.subtelny.components.core.storage.ComponentsStorage;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentContext {

    private static final String PATH_TO_SCAN = "pl.subtelny";

    private static ComponentContext context;

    private ComponentsStorage componentsStorage;

    public static void initialize(Plugin plugin) {
        if (context != null) {
            throw new IllegalStateException("Context are initialized already");
        }
        context = new ComponentContext();
        context.init(plugin);
    }

    public <T> T createComponent(ComponentPlugin componentPlugin, Class<T> clazz) {
        ComponentLoader loader = new ComponentLoader(componentsStorage.getComponents());
        try {
            return (T) loader.loadComponent(componentPlugin, clazz);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw ComponentException.of("Cannot create component for class " + clazz.getName(), e);
        }
    }

    private void init(Plugin plugin) {
        ComponentPluginsLoader loader = new ComponentPluginsLoader(plugin);
        loader.waitForAllComponentPlugins()
                .whenComplete((unused, throwable) -> loadComponents())
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
    }

    private void loadComponents() {
        loadComponentsStorage();
        loadBukkitDependencies();
        informPlugins();
    }

    private void loadComponentsStorage() {
        List<ComponentClassLoaderInfo> classLoaders = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(componentPlugin -> componentPlugin instanceof ComponentPlugin)
                .map(componentPlugin -> new ComponentClassLoaderInfo((ComponentPlugin) componentPlugin, componentPlugin.getClass().getClassLoader()))
                .collect(Collectors.toList());

        ComponentsLoader loader = new ComponentsLoader(classLoaders, PATH_TO_SCAN);
        this.componentsStorage = new ComponentsStorage(loader.loadComponents());
    }

    private void loadBukkitDependencies() {
        new ComponentBukkitDependenciesLoader(componentsStorage.getComponents().values())
                .load();
    }

    private void informPlugins() {
        getComponentsStorage().getComponents().values().stream()
                .map(ComponentObjectInfo::getComponentPlugin)
                .collect(Collectors.toList())
                .forEach(ComponentPlugin::onInitialize);
    }

    public ComponentsStorage getComponentsStorage() {
        return componentsStorage;
    }

    public static ComponentContext getContext() {
        return context;
    }

}

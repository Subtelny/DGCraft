package pl.subtelny.components.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import pl.subtelny.components.core.api.PluginData;
import pl.subtelny.components.core.api.plugin.DGPlugin;
import pl.subtelny.components.core.plugin.DependenciesInitializer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ComponentsContext {

    private static ComponentsContext instance;

    private BeanStorage beanStorage = new BeanStorage();

    public static void loadBeans() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        List<PluginData> pluginInformations = Arrays.stream(pluginManager.getPlugins())
                .filter(plugin -> plugin instanceof DGPlugin)
                .map(plugin -> (DGPlugin) plugin)
                .map(DGPlugin::getPluginInformation)
                .collect(Collectors.toList());
        new PluginBeansInitializer().loadBeans(pluginInformations);
    }

    public static void loadDependencies(DGPlugin plugin) {
        DependenciesInitializer dependenciesService = new DependenciesInitializer(plugin);
        dependenciesService.registerPluginsComponents();
    }

    public static <T> T getBean(Class<T> clazz) {
        return getInstance().beanStorage.getBean(clazz);
    }

    public static void addBeans(Map<Class, Object> beans) {
        getInstance().beanStorage.addBeans(beans);
    }

    public static <T> List<T> getBeans(Class<T> clazz) {
        return getInstance().beanStorage.getBeans(clazz);
    }

    private static ComponentsContext getInstance() {
        if (instance == null) {
            instance = new ComponentsContext();
        }
        return instance;
    }

}

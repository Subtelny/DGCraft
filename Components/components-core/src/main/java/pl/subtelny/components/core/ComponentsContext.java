package pl.subtelny.components.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import pl.subtelny.components.core.api.PluginData;
import pl.subtelny.components.core.api.module.ModuleProvider;
import pl.subtelny.components.core.api.plugin.DGPlugin;
import pl.subtelny.components.core.module.ModuleProviderImpl;
import pl.subtelny.components.core.plugin.DependenciesInitializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ComponentsContext {

    private static ComponentsContext instance;

    private final BeanStorage beanStorage = new BeanStorage();

    public static void loadBeans() {
        initModuleProvider();
        PluginManager pluginManager = Bukkit.getPluginManager();
        List<PluginData> pluginInformations = Arrays.stream(pluginManager.getPlugins())
                .filter(plugin -> plugin instanceof DGPlugin)
                .map(plugin -> (DGPlugin) plugin)
                .map(DGPlugin::getPluginInformation)
                .collect(Collectors.toList());
        loadBeans(pluginInformations);
    }

    private static void loadBeans(List<PluginData> pluginInformations) {
        List<ClassLoader> classLoaders = pluginInformations.stream()
                .map(PluginData::getClassLoader)
                .collect(Collectors.toList());
        List<String> paths = pluginInformations.stream()
                .flatMap(pluginInformation -> pluginInformation.getPaths().stream())
                .collect(Collectors.toList());
        initializeBeans(classLoaders, paths);
    }

    private static void initializeBeans(List<ClassLoader> classLoaders, List<String> paths) {
        BeansObjectsLoader loader = new BeansObjectsLoader(paths, classLoaders);
        Map<Class, Object> loadedBeans = loader.loadBeans();
        ComponentsContext.addBeans(loadedBeans);
    }

    private static void initModuleProvider() {
        Map<Class, Object> map = new HashMap<>();
        ModuleProvider moduleProvider = new ModuleProviderImpl();
        map.put(ModuleProvider.class, moduleProvider);
        addBeans(map);
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

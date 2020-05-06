package pl.subtelny.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.reflections.util.ClasspathHelper;
import pl.subtelny.components.core.BeanServiceImpl;
import pl.subtelny.components.core.api.BeanService;
import pl.subtelny.core.api.plugin.DGPlugin;
import pl.subtelny.core.dependencies.DependenciesService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Core extends DGPlugin {

    private final BeanService beanService = new BeanServiceImpl();

    @Override
    public void onLoad() {
        loadBeans();
    }

    @Override
    public void onEnabled() {
        loadDependencies();
    }

    @Override
    public void onInitialize() {

    }

    private void loadBeans() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        List<String> paths = Arrays.stream(pluginManager.getPlugins())
                .filter(plugin -> plugin instanceof DGPlugin)
                .map(plugin -> (DGPlugin) plugin)
                .flatMap(plugin -> plugin.componentsPaths().stream())
                .collect(Collectors.toList());
        ClassLoader classLoader = ClasspathHelper.staticClassLoader();
        beanService.initializeBeans(classLoader, paths);
    }

    private void loadDependencies() {
        DependenciesService dependenciesService = new DependenciesService(this, beanService);
        dependenciesService.registerPluginsComponents();
    }
}

package pl.subtelny.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import pl.subtelny.components.core.BeanServiceImpl;
import pl.subtelny.components.core.api.BeanService;
import pl.subtelny.components.core.api.PluginInformation;
import pl.subtelny.core.api.plugin.DGPlugin;
import pl.subtelny.core.dependencies.DependenciesInitializer;

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
        List<PluginInformation> pluginInformations = Arrays.stream(pluginManager.getPlugins())
                .filter(plugin -> plugin instanceof DGPlugin)
                .map(plugin -> (DGPlugin) plugin)
                .map(DGPlugin::getPluginInformation)
                .collect(Collectors.toList());
        List<ClassLoader> classLoaders = pluginInformations.stream()
                .map(PluginInformation::getClassLoader)
                .collect(Collectors.toList());
        List<String> paths = pluginInformations.stream()
                .flatMap(pluginInformation -> pluginInformation.getPaths().stream())
                .collect(Collectors.toList());
        beanService.initializeBeans(classLoaders, paths);
    }

    private void loadDependencies() {
        DependenciesInitializer dependenciesService = new DependenciesInitializer(this, beanService);
        dependenciesService.registerPluginsComponents();
    }
}

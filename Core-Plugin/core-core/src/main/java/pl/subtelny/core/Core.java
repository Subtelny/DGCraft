package pl.subtelny.core;

import com.google.common.collect.Lists;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.util.ClasspathHelper;
import pl.subtelny.components.core.BeanServiceImpl;
import pl.subtelny.components.core.api.BeanService;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.core.dependencies.DependenciesService;

import java.util.List;

public class Core extends JavaPlugin {

    private final static String PLUGINS_PATH = "pl.subtelny";

    private final BeanService beanService = new BeanServiceImpl();

    @Override
    public void onLoad() {
        loadBeans();
    }

    private void loadBeans() {
        ClassLoader classLoader = ClasspathHelper.staticClassLoader();
        List<String> paths = Lists.newArrayList(PLUGINS_PATH);
        beanService.initializeBeans(classLoader, paths);
    }

    @Override
    public void onEnable() {
        new Settings(this);
        loadDependencies();
    }

    private void loadDependencies() {
        DependenciesService dependenciesService = new DependenciesService(this, beanService);
        dependenciesService.registerPluginsComponents();
    }

}

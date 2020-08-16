package pl.subtelny.components.core;

import pl.subtelny.components.core.api.PluginData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PluginBeansInitializer {

    public void loadBeans(List<PluginData> pluginInformations) {
        List<ClassLoader> classLoaders = pluginInformations.stream()
                .map(PluginData::getClassLoader)
                .collect(Collectors.toList());
        List<String> paths = pluginInformations.stream()
                .flatMap(pluginInformation -> pluginInformation.getPaths().stream())
                .collect(Collectors.toList());
        initializeBeans(classLoaders, paths);
    }

    private void initializeBeans(List<ClassLoader> classLoaders, List<String> paths) {
        BeansObjectsLoader loader = new BeansObjectsLoader(paths, classLoaders);
        Map<Class, Object> loadedBeans = loader.loadBeans();
       ComponentsContext.addBeans(loadedBeans);
    }

}

package pl.subtelny.components.core;

import pl.subtelny.components.core.prototype.BeanPrototype;
import pl.subtelny.components.core.prototype.BeanPrototypeConstructor;
import pl.subtelny.components.core.reflections.ComponentReflections;
import pl.subtelny.components.core.reflections.ComponentScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BeansObjectsLoader {

    private final List<String> paths;

    private final List<ClassLoader> classLoaders;

    public BeansObjectsLoader(List<String> paths, List<ClassLoader> classLoaders) {
        this.paths = paths;
        this.classLoaders = classLoaders;
    }

    public Map<Class, Object> loadBeans() {
        ComponentReflections reflections = buildReflections();
        Set<Class<?>> componentClasses = reflections.getComponentTypes();
        List<BeanPrototype> beanPrototypes = loadBeanPrototypes(componentClasses);
        return initializeBeans(beanPrototypes);
    }

    private ComponentReflections buildReflections() {
        List<Object> objects = new ArrayList<>(classLoaders);
        objects.addAll(paths);
        objects.add(new ComponentScanner());
        return new ComponentReflections(objects);
    }

    private List<BeanPrototype> loadBeanPrototypes(Set<Class<?>> componentClasses) {
        return componentClasses.stream()
                .map(BeanPrototypeConstructor::new)
                .map(BeanPrototypeConstructor::loadBeanPrototype)
                .collect(Collectors.toList());
    }

    private Map<Class, Object> initializeBeans(List<BeanPrototype> beanPrototypes) {
        BeansInitializer initializer = new BeansInitializer(beanPrototypes);
        return initializer.initializeBeans();
    }

}

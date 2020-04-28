package pl.subtelny.components.core;

import com.google.common.collect.Lists;
import pl.subtelny.components.core.prototype.BeanPrototype;
import pl.subtelny.components.core.prototype.BeanPrototypeConstructor;
import pl.subtelny.components.core.reflections.ComponentReflections;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BeansLoader {

    private final List<String> paths;

    private final ClassLoader classLoader;

    public BeansLoader(List<String> paths, ClassLoader classLoader) {
        this.paths = paths;
        this.classLoader = classLoader;
    }

    public Map<Class, Object> loadBeans() {
        ComponentReflections reflections = buildReflections();
        Set<Class<?>> componentClasses = reflections.getComponentTypes();
        List<BeanPrototype> beanPrototypes = loadBeanPrototypes(componentClasses);
        return initializeBeans(beanPrototypes);
    }

    private ComponentReflections buildReflections() {
        List<Object> objects = Lists.newArrayList(classLoader);
        objects.addAll(paths);
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

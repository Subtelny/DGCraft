package pl.subtelny.components.core;

import org.reflections.Reflections;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.prototype.BeanPrototype;
import pl.subtelny.components.core.prototype.BeanPrototypeConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BeansLoader {

    private final List<String> paths;

    public BeansLoader(List<String> paths) {
        this.paths = paths;
    }

    public Map<String, Object> loadBeans() {
        Reflections reflections = buildReflections(paths);
        Set<Class<?>> componentClasses = findComponentClasses(reflections);
        List<BeanPrototype> beanPrototypes = loadBeanPrototypes(componentClasses);
        return initializeBeans(beanPrototypes);
    }

    private Reflections buildReflections(List<String> paths) {
        return new Reflections(paths.toArray());
    }

    private Set<Class<?>> findComponentClasses(Reflections reflections) {
        return reflections.getTypesAnnotatedWith(Component.class)
                .stream()
                .filter(aClass -> !aClass.isAnnotation())
                .collect(Collectors.toSet());
    }

    private List<BeanPrototype> loadBeanPrototypes(Set<Class<?>> componentClasses) {
        return componentClasses.stream()
                .map(BeanPrototypeConstructor::new)
                .map(BeanPrototypeConstructor::loadBeanPrototype)
                .collect(Collectors.toList());
    }

    private Map<String, Object> initializeBeans(List<BeanPrototype> beanPrototypes) {
        BeansInitializer initializer = new BeansInitializer(beanPrototypes);
        Map<Class, Object> initializedBeans = initializer.initializeBeans();
        return initializedBeans.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        classObjectEntry -> classObjectEntry.getKey().getName(),
                        Map.Entry::getValue)
                );
    }

}

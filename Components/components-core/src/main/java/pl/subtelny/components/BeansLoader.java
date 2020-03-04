package pl.subtelny.components;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import pl.subtelny.components.api.Component;
import pl.subtelny.components.prototype.BeanPrototype;
import pl.subtelny.components.prototype.BeanPrototypeConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BeansLoader {

    private final String path;

    public BeansLoader(String path) {
        this.path = path;
    }

    public Map<String, Object> loadBeans() {
        Reflections reflections = buildReflections(path);
        Set<Class<?>> componentClasses = findComponentClasses(reflections);
        List<BeanPrototype> beanPrototypes = loadBeanPrototypes(componentClasses);
        return initializeBeans(beanPrototypes);
    }

    private Reflections buildReflections(String path) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setClassLoaders(new ClassLoader[]{ClasspathHelper.staticClassLoader()});
        builder.addUrls(ClasspathHelper.forPackage(path));
        return new Reflections(path);
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

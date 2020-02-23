package pl.subtelny.components.core;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import pl.subtelny.components.api.Component;

public class BeanLoader {

    public Map<String, Object> loadBeans(String path, ClassLoader classLoader) {
        Reflections reflections = buildReflections(path, classLoader);

        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class)
                .stream()
                .filter(aClass -> !aClass.isAnnotation())
                .collect(Collectors.toSet());

        return typesAnnotatedWith.stream().collect(Collectors.toMap( i -> i.getName(), i -> i));
    }

    private Reflections buildReflections(String path, ClassLoader classLoader) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setClassLoaders(new ClassLoader[]{classLoader});
        builder.setUrls(ClasspathHelper.forPackage(path));
        return new Reflections(builder);
    }

}

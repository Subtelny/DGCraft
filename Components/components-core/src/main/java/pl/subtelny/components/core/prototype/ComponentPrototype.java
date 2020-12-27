package pl.subtelny.components.core.prototype;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ComponentPrototype {

    private final Class clazz;

    private final Constructor constructor;

    private final ComponentPlugin componentPlugin;

    public ComponentPrototype(Class clazz, Constructor constructor, ComponentPlugin componentPlugin) {
        this.clazz = clazz;
        this.constructor = constructor;
        this.componentPlugin = componentPlugin;
    }

    public Class getClazz() {
        return clazz;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public ComponentPlugin getComponentPlugin() {
        return componentPlugin;
    }

    public static ComponentPrototype from(Class clazz, ComponentPlugin componentPlugin) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        List<Constructor> autowiredConstructors = Arrays.stream(constructors)
                .filter(constructor1 -> constructor1.getAnnotation(Autowired.class) != null)
                .collect(Collectors.toList());

        if (autowiredConstructors.size() == 0) {
            autowiredConstructors = Arrays.stream(constructors)
                    .filter(constructor1 -> constructor1.getParameterCount() == 0)
                    .collect(Collectors.toList());
        }

        if (autowiredConstructors.size() != 1) {
            throw new IllegalStateException("Not found proper component constructor for class " + clazz.getName());
        }
        return new ComponentPrototype(clazz, autowiredConstructors.get(0), componentPlugin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentPrototype that = (ComponentPrototype) o;
        return Objects.equals(clazz, that.clazz) &&
                Objects.equals(constructor, that.constructor) &&
                Objects.equals(componentPlugin, that.componentPlugin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, constructor, componentPlugin);
    }
}

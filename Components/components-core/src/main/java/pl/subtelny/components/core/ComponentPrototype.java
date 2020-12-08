package pl.subtelny.components.core;

import pl.subtelny.components.core.api.Autowired;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ComponentPrototype {

    private final Class clazz;

    private final Constructor constructor;

    public ComponentPrototype(Class clazz, Constructor constructor) {
        this.clazz = clazz;
        this.constructor = constructor;
    }

    public Class getClazz() {
        return clazz;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public static ComponentPrototype from(Class clazz) {
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
        return new ComponentPrototype(clazz, autowiredConstructors.get(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentPrototype that = (ComponentPrototype) o;
        return Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }
}

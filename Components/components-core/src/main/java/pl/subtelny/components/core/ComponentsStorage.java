package pl.subtelny.components.core;

import pl.subtelny.components.core.api.ComponentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentsStorage {

    private final Map<Class, Object> components;

    public ComponentsStorage(Map<Class, Object> components) {
        this.components = components;
    }

    public <T> List<T> getComponents(Class<T> clazz) {
        return components.values().stream()
                .filter(component -> clazz.isAssignableFrom(component.getClass()))
                .map(bean -> (T) bean)
                .collect(Collectors.toList());
    }

    public <T> T getComponent(Class<?> clazz) {
        return components.values().stream()
                .filter(component -> clazz.isAssignableFrom(component.getClass()))
                .map(component -> (T) component)
                .findAny()
                .orElseThrow(() -> ComponentException.of("Not found any bean for class " + clazz.getName()));
    }

    public Map<Class, Object> getComponents() {
        return new HashMap<>(components);
    }
}

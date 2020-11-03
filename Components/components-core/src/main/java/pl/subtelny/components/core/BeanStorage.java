package pl.subtelny.components.core;

import com.google.common.collect.Maps;
import pl.subtelny.components.core.api.BeanContextException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanStorage {

    private final Map<Class, Object> beans = Maps.newHashMap();

    public void addBeans(Map<Class, Object> beans) {
        this.beans.putAll(beans);
    }

    public <T> List<T> getBeans(Class<T> clazz) {
        return beans.values().stream()
                .filter(bean -> clazz.isAssignableFrom(bean.getClass()))
                .map(bean -> (T) bean)
                .collect(Collectors.toList());
    }

    public <T> T getBean(Class<?> clazz) {
        return beans.values().stream()
                .filter(bean -> clazz.isAssignableFrom(bean.getClass()))
                .map(bean -> (T) bean)
                .findAny()
                .orElseThrow(() -> BeanContextException.of("Not found any bean for class " + clazz.getName()));
    }

    public Map<Class, Object> getBeans() {
        return new HashMap<>(beans);
    }

}

package pl.subtelny.components.core;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanStorage {

    private Map<String, Object> beans = Maps.newHashMap();

    public void addBean(String beanName, Object object) {
        if (beans.containsKey(beanName)) {
            throw BeanContextException.of("Bean with name " + beanName + " already exist");
        }
        beans.put(beanName, object);
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

    public Object getBean(String beanName) {
        if (!beans.containsKey(beanName)) {
            throw BeanContextException.of("Bean with name " + beanName + " not exist");
        }
        return beans.get(beanName);
    }

    public <T> T getBean(String beanName, Class<T> clazz) {
        Object bean = getBean(beanName);
        if (bean.getClass().isAssignableFrom(clazz)) {
            return (T) bean;
        }
        throw BeanContextException.of("Cannot cast bean " + beanName + " into " + clazz.getName() + " class");
    }

    public Map<String, Object> getBeans() {
        return new HashMap<>(beans);
    }

}
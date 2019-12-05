package pl.subtelny.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class BeanContext {

    private static Map<String, Object> beans = new HashMap<>();

    public static void addBean(String beanName, Object object) {
        if(beans.containsKey(beanName)) {
            throw new BeanContextException("Bean with name " + beanName + " already exist");
        }
        beans.put(beanName, object);
    }

    public static <T> List<T> getBeans(Class<T> clazz) {
        return (List<T>) beans.values().stream().filter(i -> clazz.isAssignableFrom(i.getClass())).collect(Collectors.toList());
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        Object bean = getBean(beanName);
        if(bean != null) {
            if (bean.getClass().isAssignableFrom(clazz)) {
                return (T) bean;
            }
        }
        throw new BeanContextException("Bean with name " + beanName + " its not " + clazz.getName() + " class");
    }

    public static <T> T getBean(Class<?> clazz) {
        return (T) beans.values().stream().filter(i -> clazz.isAssignableFrom(i.getClass())).findAny().get();
    }

    public static Object getBean(String beanName) {
        if(!containBean(beanName)) {
            throw new BeanContextException("Bean with name " + beanName + " not exist");
        }
        return beans.get(beanName);
    }

    public static boolean containBean(String beanName) {
        return beanName.contains(beanName);
    }

    public static Map<String, Object> getBeans() {
        return new HashMap<>(beans);
    }
}

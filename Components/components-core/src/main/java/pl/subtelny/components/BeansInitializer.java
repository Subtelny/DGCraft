package pl.subtelny.components;

import com.google.common.collect.Maps;
import pl.subtelny.components.api.BeanContextException;
import pl.subtelny.components.prototype.BeanPrototype;
import pl.subtelny.components.validate.BeanValidatorService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeansInitializer {

    private final Map<Class, BeanPrototype> beanPrototypes;

    private final Map<Class, Object> initializedBeans = Maps.newHashMap();

    public BeansInitializer(List<BeanPrototype> beanPrototypes) {
        new BeanValidatorService().validate(beanPrototypes);
        this.beanPrototypes = beanPrototypes.stream()
                .collect(Collectors.toMap(BeanPrototype::getClazz, beanPrototype -> beanPrototype));
    }

    public Map<Class, Object> initializeBeans() {
        beanPrototypes.keySet().forEach(this::computeBeanIfAbsent);
        return initializedBeans;
    }

    private Object computeBeanIfAbsent(Class clazz) {
        BeanPrototype beanPrototype = beanPrototypes.get(clazz);
        return initializedBeans.computeIfAbsent(clazz, aClass -> createBeanInstance(beanPrototype));
    }

    private Object createBeanInstance(BeanPrototype beanPrototype) {
        Constructor constructor = beanPrototype.getConstructor();
        Class[] parameterTypes = constructor.getParameterTypes();

        List<Object> parameters = Arrays.stream(parameterTypes)
                .map(this::computeBeanIfAbsent)
                .collect(Collectors.toList());

        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            String beanName = beanPrototype.getClazz().getName();
            String message = String.format("Could not create instance of bean %s: %s", beanName, e.getMessage());
            throw BeanContextException.of(message, e);
        }
    }

}

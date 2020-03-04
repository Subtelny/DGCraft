package pl.subtelny.components;

import com.google.common.collect.Maps;
import pl.subtelny.components.api.BeanContextException;
import pl.subtelny.components.prototype.BeanPrototype;
import pl.subtelny.components.util.BeanUtil;
import pl.subtelny.components.validate.BeanValidatorService;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeansInitializer {

    private final List<BeanPrototype> beanPrototypes;

    private final Map<Class, Object> initializedBeans = Maps.newConcurrentMap();

    public BeansInitializer(List<BeanPrototype> beanPrototypes) {
        new BeanValidatorService().validate(beanPrototypes);
        this.beanPrototypes = beanPrototypes;
    }

    public Map<Class, Object> initializeBeans() {
        beanPrototypes.forEach(this::computeBeanIfAbsent);
        return initializedBeans;
    }

    private Object computeBeanIfAbsent(BeanPrototype beanPrototype) {
        Class clazz = beanPrototype.getClazz();
        return initializedBeans.computeIfAbsent(clazz, aClass -> createBeanInstance(beanPrototype));
    }

    private Object createBeanInstance(BeanPrototype beanPrototype) {
        Constructor constructor = beanPrototype.getConstructor();
        Parameter[] parameters = constructor.getParameters();

        List<Object> beans = Arrays.stream(parameters)
                .map(this::computeParameter)
                .collect(Collectors.toList());

        try {
            return constructor.newInstance(beans.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            String beanName = beanPrototype.getClazz().getName();
            String message = String.format("Could not create instance of bean %s: %s", beanName, e.getMessage());
            throw BeanContextException.of(message, e);
        }
    }

    private Object computeParameter(Parameter parameter) {
        Class<?> type = parameter.getType();
        if (CollectionUtil.isCollection(type)) {
            Class<?> genericType = BeanUtil.genericTypeFromParemeter(parameter);
            List<BeanPrototype> beanPrototypes = findBeanPrototypes(genericType);
            return CollectionUtil.streamToCollectionByType(type,
                    beanPrototypes.stream().map(this::computeBeanIfAbsent));
        }
        BeanPrototype beanPrototype = findBeanPrototype(type);
        return computeBeanIfAbsent(beanPrototype);
    }

    private BeanPrototype findBeanPrototype(Class clazz) {
        List<BeanPrototype> prototypes = findBeanPrototypes(clazz);
        if (prototypes.size() > 1) {
            throw BeanContextException.of("Found more than one bean for class " + clazz);
        }
        return prototypes.stream()
                .findAny()
                .orElseThrow(() -> BeanContextException.of("Not found prototype for class " + clazz));
    }

    private List<BeanPrototype> findBeanPrototypes(Class clazz) {
        return beanPrototypes.stream()
                .filter(beanPrototype -> beanPrototype.isBeanPrototypeClass(clazz))
                .collect(Collectors.toList());
    }

}

package pl.subtelny.components.validate;

import pl.subtelny.components.api.BeanContextException;
import pl.subtelny.components.prototype.BeanPrototype;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BeanCyclicDependencyValidator extends BeanValidator {

    public BeanCyclicDependencyValidator(List<BeanPrototype> beanPrototypes) {
        super(beanPrototypes);
    }

    @Override
    public void validate(BeanPrototype beanPrototype) {
        if (hasCyclicDependency(beanPrototype)) {
            throw BeanContextException.of(String.format("Found cyclic dependency in bean class %s", beanPrototype.getClazz().getName()));
        }
    }

    private boolean hasCyclicDependency(BeanPrototype beanPrototype) {
        Map<Class, Constructor> mappedBeanPrototypes = getBeanPrototypes().stream()
                .collect(Collectors.toMap(BeanPrototype::getClazz, BeanPrototype::getConstructor));
        System.out.println("  --- ");
        mappedBeanPrototypes.forEach((aClass, constructor) -> {
            System.out.println(aClass + " - " + constructor.getName());
        });
        System.out.println("  --- ");

        Class clazz = beanPrototype.getClazz();
        Constructor clazzConstructor = beanPrototype.getConstructor();
        return containClassInAnyConstructor(clazz, clazzConstructor, mappedBeanPrototypes);
    }

    private boolean containClassInAnyConstructor(Class toFind, Constructor constructor,
                                                 Map<Class, Constructor> mappedBeanPrototypes) {
        Class[] parameterTypes = constructor.getParameterTypes();
        return Arrays.stream(parameterTypes)
                .anyMatch(getMatchClassPredicate(toFind, mappedBeanPrototypes));
    }

    private Predicate<Class> getMatchClassPredicate(Class toFind, Map<Class, Constructor> mappedBeanPrototypes) {
        return clazz -> {
            if (clazz.equals(toFind)) {
                return true;
            }
            Constructor constructor = mappedBeanPrototypes.get(clazz);
            System.out.println(String.format("Constructor for class %s is - %s", clazz, constructor));
            return containClassInAnyConstructor(toFind, constructor, mappedBeanPrototypes);
        };
    }

}

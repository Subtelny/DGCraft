package pl.subtelny.components.core.validate;

import pl.subtelny.components.core.api.BeanContextException;
import pl.subtelny.components.core.prototype.BeanPrototype;
import pl.subtelny.components.core.util.BeanUtil;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        Constructor clazzConstructor = beanPrototype.getConstructor();
        return containClassInAnyConstructor(beanPrototype, clazzConstructor);
    }

    private boolean containClassInAnyConstructor(BeanPrototype toFind, Constructor constructor) {
        Parameter[] parameters = constructor.getParameters();
        return Arrays.stream(parameters)
                .anyMatch(parameter -> findBeanPrototypeInParameter(toFind, parameter));
    }

    private boolean findBeanPrototypeInParameter(BeanPrototype toFind, Parameter parameter) {
        Class<?> parameterType = parameter.getType();
        if (CollectionUtil.isCollection(parameterType)) {
            parameterType = BeanUtil.genericTypeFromParemeter(parameter);
        }
        List<BeanPrototype> beanPrototypes = getBeanPrototypesByClass(parameterType);
        return beanPrototypes.contains(toFind);
    }

}

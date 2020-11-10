package pl.subtelny.components.core2;

import pl.subtelny.components.core.util.BeanUtil;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentCyclicDependencyValidator {

    private final Set<ComponentPrototype> componentPrototypes;

    public ComponentCyclicDependencyValidator(Set<ComponentPrototype> componentPrototypes) {
        this.componentPrototypes = componentPrototypes;
    }

    public void validate(ComponentPrototype componentPrototype) {
        if (hasCyclicDependency(componentPrototype)) {
            throw ComponentException.of("Found cyclic dependency in bean class " + componentPrototype.getClazz().getName());
        }
    }

    private boolean hasCyclicDependency(ComponentPrototype componentPrototype) {
        Constructor clazzConstructor = componentPrototype.getConstructor();
        return containClassInAnyConstructor(componentPrototype, clazzConstructor);
    }

    private boolean containClassInAnyConstructor(ComponentPrototype toFind, Constructor constructor) {
        Parameter[] parameters = constructor.getParameters();
        return Arrays.stream(parameters)
                .anyMatch(parameter -> findBeanPrototypeInParameter(toFind, parameter));
    }

    private boolean findBeanPrototypeInParameter(ComponentPrototype toFind, Parameter parameter) {
        Class<?> parameterType = parameter.getType();
        if (CollectionUtil.isCollection(parameterType)) {
            parameterType = BeanUtil.genericTypeFromParemeter(parameter);
        }
        List<ComponentPrototype> componentPrototypes = getComponentPrototypesByClass(parameterType);
        return componentPrototypes.contains(toFind);
    }

    public List<ComponentPrototype> getComponentPrototypesByClass(Class clazz) {
        return componentPrototypes.stream()
                .filter(beanPrototype -> beanPrototype.isComponentPrototypeClass(clazz))
                .collect(Collectors.toList());
    }
}

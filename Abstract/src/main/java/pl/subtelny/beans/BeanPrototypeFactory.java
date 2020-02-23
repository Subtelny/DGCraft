package pl.subtelny.beans;

import pl.subtelny.beans.command.HeadCommand;
import pl.subtelny.beans.command.SubCommand;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class BeanPrototypeFactory {

    private final Set<Class<?>> components;

    BeanPrototypeFactory(Set<Class<?>> components) {
        this.components = components;
    }

    List<BeanPrototype> preparePrototypes() throws BeanPreparePrototypeException {
        List<BeanPrototype> prototypes = new ArrayList<>();
        for (Class<?> component : components) {
            if (!hasComponentBehaviorAnnotation(component)) {
                throw new BeanPreparePrototypeException("Class " + component.getName() + " is not component!");
            }
            Constructor<?> constructor = findProperConstructor(component);
            prototypes.add(new BeanPrototype(component, constructor));
        }

        for (BeanPrototype prototype : prototypes) {
            if (hasCircuralDependency(prototype, prototypes, new ArrayList<>())) {
                throw new BeanPreparePrototypeException("Found circural dependency in prototype " + prototype.getComponent().getName());
            }
        }
        return prototypes;
    }

    private Constructor<?> findProperConstructor(Class<?> clazz) throws BeanPreparePrototypeException {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> properConstructor = null;
        Constructor<?> emptyConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (properConstructor != null) {
                throw new BeanPreparePrototypeException("In class " + clazz.getName() + " is more than one autowired constructor");
            }
            Parameter[] parameters = constructor.getParameters();
            if (constructor.getAnnotation(Autowired.class) != null) {
                properConstructor = constructor;
                for (Parameter parameter : parameters) {
                    if (!isProperParameter(parameter)) {
                        throw new BeanPreparePrototypeException(String.format("Not found %s class in components while creating class %s",
                                parameter.getType().getName(),
                                clazz.getName()));
                    }
                }
            }
            if (parameters.length == 0) {
                emptyConstructor = constructor;
            }
        }
        if (properConstructor != null) {
            return properConstructor;
        }
        if (emptyConstructor != null) {
            return emptyConstructor;
        }
        throw new BeanPreparePrototypeException("Not found proper constructor for " + clazz.getName());
    }

    private boolean isProperParameter(Parameter parameter) throws BeanPreparePrototypeException {
        if (parameter.getParameterizedType() instanceof ParameterizedType) {
            Class<?> genericClass = BeanUtil.genericTypeFromParemeter(parameter);
            return containComponent(genericClass);
        }
        return containComponent(parameter.getType());
    }

    private boolean containComponent(Class<?> searchTo) {
        return components.stream()
                .anyMatch(searchTo::isAssignableFrom);
    }

    private boolean hasCircuralDependency(BeanPrototype examined, List<BeanPrototype> beanPrototypes, List<Class<?>> visitedClasses) throws BeanPreparePrototypeException {
        if (visitedClasses.contains(examined.getComponent())) {
            return true;
        }
        List<Class<?>> clonedVisitedClasses = new ArrayList<>(visitedClasses);
        clonedVisitedClasses.add(examined.getComponent());

        List<Boolean> hasCircural = new ArrayList<>();

        for (Parameter parameter : examined.getConstructor().getParameters()) {
            Class<?> toSearch = parameter.getType();
            if (parameter.getParameterizedType() instanceof ParameterizedType) {
                toSearch = BeanUtil.genericTypeFromParemeter(parameter);
            }
            List<BeanPrototype> prototypes = BeanUtil.allPrototypesForClass(toSearch, beanPrototypes);
            for (BeanPrototype prototype : prototypes) {
                hasCircural.add(hasCircuralDependency(prototype, beanPrototypes, clonedVisitedClasses));
            }
        }
        return hasCircural.stream().anyMatch(i -> i);
    }

    private boolean hasComponentBehaviorAnnotation(Class<?> clazz) {
        return clazz.isAnnotationPresent(Component.class)
                || clazz.isAnnotationPresent(SubCommand.class)
                || clazz.isAnnotationPresent(HeadCommand.class);
    }

}

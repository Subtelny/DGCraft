package pl.subtelny.components.core;

import pl.subtelny.components.core.api.ComponentException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentCyclicDependencyValidator {

    private final Set<ComponentPrototype> componentPrototypes;

    public ComponentCyclicDependencyValidator(Set<ComponentPrototype> componentPrototypes) {
        this.componentPrototypes = componentPrototypes;
    }

    public void validate(ComponentPrototype componentPrototype) {
        Type[] parameters = componentPrototype.getConstructor().getGenericParameterTypes();
        for (Type parameter : parameters) {
            findPrototypesByType(parameter).forEach(prototype -> validateFor(componentPrototype, prototype));
        }
    }

    public void validateFor(ComponentPrototype toFind, ComponentPrototype checking) {
        if (toFind.equals(checking)) {
            String toFindName = toFind.getClazz().getName();
            String checkingName = checking.getClazz().getName();
            throw ComponentException.of("Found cyclic dependency: " + toFindName + " - " + checkingName);
        }

        Type[] parameters = checking.getConstructor().getGenericParameterTypes();
        for (Type parameter : parameters) {
            findPrototypesByType(parameter).forEach(prototype -> validateFor(toFind, prototype));
        }
    }

    public List<ComponentPrototype> findPrototypesByType(Type type) {
        return componentPrototypes.stream()
                .filter(componentPrototype -> ComponentUtil.classMatchedToType(componentPrototype.getClazz(), type))
                .collect(Collectors.toList());
    }

}

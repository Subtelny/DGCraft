package pl.subtelny.components.core.validation;

import pl.subtelny.components.core.ComponentUtil;
import pl.subtelny.components.core.api.ComponentException;
import pl.subtelny.components.core.prototype.ComponentPrototype;
import pl.subtelny.utilities.ClassUtil;

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
        String startPath = componentPrototype.getClazz().getSimpleName();

        Type[] parameters = componentPrototype.getConstructor().getGenericParameterTypes();
        for (Type parameter : parameters) {
            try {
                String newPath = startPath + " -> " + ClassUtil.getTypeName(parameter);
                findPrototypesByType(parameter).forEach(prototype -> validateFor(componentPrototype, prototype, newPath));
            } catch (ComponentException | StackOverflowError e) {
                String componentClazz = componentPrototype.getClazz().getSimpleName();
                String parameterName = ClassUtil.getTypeName(parameter);
                throw ComponentException.of("Found cyclic dependency in component " + componentClazz + ". Parameter: " + parameterName, e);
            }
        }
    }

    private void validateFor(ComponentPrototype toFind, ComponentPrototype checking, String path) {
        if (toFind.equals(checking)) {
            String toFindName = toFind.getClazz().getSimpleName();
            String newPath = path + " -> " + toFindName;
            throw ComponentException.of("Validate failure at path: " + newPath);
        }

        Type[] parameters = checking.getConstructor().getGenericParameterTypes();
        try {
            for (Type parameter : parameters) {
                String newPath = path + " -> " + ClassUtil.getTypeName(parameter);
                findPrototypesByType(parameter)
                        .forEach(prototype -> validateFor(toFind, prototype, newPath));
            }
        } catch (StackOverflowError ignore) {

        }
    }

    private List<ComponentPrototype> findPrototypesByType(Type type) {
        return componentPrototypes.stream()
                .filter(componentPrototype -> ComponentUtil.classMatchedToType(componentPrototype.getClazz(), type))
                .collect(Collectors.toList());
    }

}

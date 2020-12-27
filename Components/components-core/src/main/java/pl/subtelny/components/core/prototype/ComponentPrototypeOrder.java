package pl.subtelny.components.core.prototype;

import pl.subtelny.components.core.ComponentUtil;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentPrototypeOrder {

    private final ComponentPrototype prototype;

    private final Set<ComponentPrototype> allPrototypes;

    public ComponentPrototypeOrder(ComponentPrototype prototype, Set<ComponentPrototype> allPrototypes) {
        this.prototype = prototype;
        this.allPrototypes = allPrototypes;
    }

    public Set<ComponentPrototype> getPrototypesHasToBeInitializedBefore() {
        return allPrototypes.stream()
                .filter(componentPrototype -> hasRelationTo(componentPrototype, prototype))
                .collect(Collectors.toSet());
    }

    public boolean hasRelationTo(ComponentPrototype searchFor, ComponentPrototype in) {
        Type[] inParameters = in.getConstructor().getGenericParameterTypes();
        List<ComponentPrototype> inPrototypes = Arrays.stream(inParameters)
                .flatMap(type -> findPrototypesByType(type).stream())
                .collect(Collectors.toList());
        return inPrototypes.stream()
                .anyMatch(inPrototype -> searchFor.equals(inPrototype) || hasRelationTo(searchFor, inPrototype));
    }

    public List<ComponentPrototype> findPrototypesByType(Type type) {
        return allPrototypes.stream()
                .filter(componentPrototype -> ComponentUtil.classMatchedToType(componentPrototype.getClazz(), type))
                .collect(Collectors.toList());
    }

    public ComponentPrototype getPrototype() {
        return prototype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentPrototypeOrder that = (ComponentPrototypeOrder) o;
        return Objects.equals(prototype, that.prototype) &&
                Objects.equals(allPrototypes, that.allPrototypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prototype, allPrototypes);
    }
}

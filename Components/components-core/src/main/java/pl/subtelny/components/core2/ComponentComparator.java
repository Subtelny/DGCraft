package pl.subtelny.components.core2;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

public class ComponentComparator implements Comparator<ComponentPrototype> {

    private final Set<ComponentPrototype> allPrototypes;

    public ComponentComparator(Set<ComponentPrototype> allPrototypes) {
        this.allPrototypes = allPrototypes;
    }

    @Override
    public int compare(ComponentPrototype prototypeFirst, ComponentPrototype prototypeSecond) {
        return Long.compare(countDependencies(prototypeFirst), countDependencies(prototypeSecond));
    }

    private long countDependencies(ComponentPrototype prototype) {
        Type[] parameters = prototype.getConstructor().getGenericParameterTypes();
        return allPrototypes.parallelStream()
                .filter(componentPrototype -> componentMatchedToAnyType(componentPrototype, parameters))
                .count();
    }

    private boolean componentMatchedToAnyType(ComponentPrototype prototype, Type[] types) {
        return Arrays.stream(types).anyMatch(type -> ComponentUtil.typeMatchedToClass(prototype.getClazz(), type));
    }

}

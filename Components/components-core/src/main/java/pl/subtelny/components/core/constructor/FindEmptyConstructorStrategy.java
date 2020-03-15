package pl.subtelny.components.core.constructor;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;

public class FindEmptyConstructorStrategy implements FindBeanConstructorStrategy {

    @Override
    public Optional<Constructor> findConstructor(List<Constructor> constructors) {
        return constructors.stream()
                .filter(constructor -> constructor.getParameters().length == 0)
                .findAny();
    }

}

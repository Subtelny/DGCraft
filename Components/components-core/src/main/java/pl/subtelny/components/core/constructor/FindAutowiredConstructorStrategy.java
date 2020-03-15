package pl.subtelny.components.core.constructor;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.utilities.exception.ValidationException;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindAutowiredConstructorStrategy implements FindBeanConstructorStrategy {

    @Override
    public Optional<Constructor> findConstructor(List<Constructor> constructors) {
        List<Constructor> autowiredConstructors = constructors.stream()
                .filter(constructor -> constructor.getAnnotation(Autowired.class) != null)
                .collect(Collectors.toList());

        if (autowiredConstructors.size() > 1) {
            throw ValidationException.of("Found more than one autowired constructor");
        }
        return autowiredConstructors.stream()
                .findAny();
    }

}

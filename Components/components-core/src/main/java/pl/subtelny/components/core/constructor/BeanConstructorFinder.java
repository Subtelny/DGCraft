package pl.subtelny.components.core.constructor;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BeanConstructorFinder {

    public static Optional<Constructor> findConstructor(Class clazz) {
        List<Constructor> constructors = Arrays.asList(clazz.getConstructors());
        Optional<Constructor> autowiredConstructorOpt = findAutowiredConstructor(constructors);
        return autowiredConstructorOpt
                .or(() -> findEmptyConstructor(constructors));
    }

    private static Optional<Constructor> findEmptyConstructor(List<Constructor> constructors) {
        return new FindEmptyConstructorStrategy().findConstructor(constructors);
    }

    private static Optional<Constructor> findAutowiredConstructor(List<Constructor> constructors) {
        return new FindAutowiredConstructorStrategy().findConstructor(constructors);
    }

}

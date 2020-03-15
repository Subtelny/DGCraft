package pl.subtelny.components.core.prototype;

import pl.subtelny.components.core.api.BeanContextException;
import pl.subtelny.components.core.constructor.BeanConstructorFinder;
import pl.subtelny.utilities.exception.ValidationException;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class BeanPrototypeConstructor {

    private final Class clazz;

    public BeanPrototypeConstructor(Class clazz) {
        this.clazz = clazz;
    }

    public BeanPrototype loadBeanPrototype() {
        Constructor constructor = getClassConstructor();
        return new BeanPrototype(clazz, constructor);
    }

    private Constructor getClassConstructor() {
        Optional<Constructor> constructor;
        String className = clazz.getName();
        try {
            constructor = BeanConstructorFinder.findConstructor(clazz);
        } catch (ValidationException e) {
            throw BeanContextException.of(String.format("[%s] - %s", className, e.getMessage()), e);
        }
        return constructor.orElseThrow(() ->
                BeanContextException.of(String.format("Not found proper constructor for class %s", className)));
    }
}

package pl.subtelny.beans;

import java.lang.reflect.Constructor;

public class BeanPrototype {

    private final Class<?> component;

    private final Constructor<?> constructor;

    public BeanPrototype(Class<?> component, Constructor<?> constructor) {
        this.component = component;
        this.constructor = constructor;
    }

    public Class<?> getComponent() {
        return component;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }
}

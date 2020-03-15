package pl.subtelny.components.core.prototype;

import com.google.common.base.Preconditions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeanPrototype {

    private final Class clazz;

    private final Constructor constructor;

    public BeanPrototype(Class clazz, Constructor constructor) {
        this.clazz = Preconditions.checkNotNull(clazz);
        this.constructor = Preconditions.checkNotNull(constructor);
    }

    public boolean isBeanPrototypeClass(Class clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }

    public Class getClazz() {
        return clazz;
    }

    public Constructor getConstructor() {
        return constructor;
    }
}

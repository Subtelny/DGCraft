package pl.subtelny.components.core.prototype;

import com.google.common.base.Preconditions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanPrototype that = (BeanPrototype) o;
        return Objects.equals(clazz, that.clazz) &&
                Objects.equals(constructor, that.constructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, constructor);
    }
}

package pl.subtelny.beans;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class BeanUtil {

    public static Class<?> genericTypeFromParemeter(Parameter parameter) throws BeanPreparePrototypeException {
        Type type = parameter.getParameterizedType();
        if (!(type instanceof ParameterizedType)) {
            throw new BeanPreparePrototypeException("Type " + type.getTypeName() + " is not ParameterizedType");
        }
        ParameterizedType parameterizedType = ((ParameterizedType) type);
        Type genericType = parameterizedType.getActualTypeArguments()[0];
        String genericName = genericType.getTypeName();
        try {
            return Class.forName(genericName);
        } catch (ClassNotFoundException e) {
            throw new BeanPreparePrototypeException("Not found class " + genericName + " in generic type " + parameterizedType.toString());
        }
    }

    public static Optional<BeanPrototype> prototypeForClass(Class<?> genericClass, List<BeanPrototype> beanPrototypes) {
        return beanPrototypes.stream()
                .filter(i -> genericClass.isAssignableFrom(i.getComponent()))
                .findAny();
    }

    public static List<BeanPrototype> allPrototypesForClass(Class<?> genericClass, List<BeanPrototype> beanPrototypes) {
        return beanPrototypes.stream()
                .filter(i -> genericClass.isAssignableFrom(i.getComponent()))
                .collect(Collectors.toList());
    }

}

package pl.subtelny.components.core2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

public final class ComponentUtil {

    public static boolean typeMatchedToClass(Class clazz, Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (isCollection(rawType)) {
                Type argument = parameterizedType.getActualTypeArguments()[0];
                return typeMatchedToClass(clazz, argument);
            }

            Type[] genericInterfaces = clazz.getGenericInterfaces();
            return Arrays.stream(genericInterfaces)
                    .anyMatch(type1 -> assignableTypes(type1, type));
        }
        return ((Class<?>) type).isAssignableFrom(clazz);
    }

    public static boolean isCollection(Type type) {
        return type instanceof Class<?> && Collection.class.isAssignableFrom((Class<?>) type);
    }

    private static boolean assignableTypes(Type type, Type typeSecond) {
        if (type instanceof ParameterizedType && typeSecond instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            ParameterizedType paramTypeSecond = (ParameterizedType) typeSecond;

            if (!paramType.getRawType().equals(paramTypeSecond.getRawType())) {
                return false;
            }

            Type[] paramTypeArgs = paramType.getActualTypeArguments();
            Type[] paramTypeSecondArgs = paramTypeSecond.getActualTypeArguments();

            if (paramTypeArgs.length == paramTypeSecondArgs.length) {
                return IntStream.range(0, paramTypeArgs.length).allMatch(i -> assignableTypes(paramTypeArgs[i], paramTypeSecondArgs[i]));
            } else {
                return false;
            }
        }
        if (type instanceof Class<?> && typeSecond instanceof Class<?>) {
            return ((Class<?>) typeSecond).isAssignableFrom((Class<?>) type);
        }
        return false;
    }

}

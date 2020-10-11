package pl.subtelny.components.core.util;

import pl.subtelny.components.core.api.BeanContextException;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class BeanUtil {

    public static Class<?> genericTypeFromParemeter(Parameter parameter) throws BeanContextException {
        Type type = parameter.getParameterizedType();
        if (!(type instanceof ParameterizedType)) {
            throw BeanContextException.of("Type " + type.getTypeName() + " is not ParameterizedType");
        }
        String genericName = getGenericName(type);
        try {
            return Class.forName(genericName);
        } catch (ClassNotFoundException e) {
            throw BeanContextException.of("Not found class " + genericName + " in generic type " + type.toString());
        }
    }

    public static String getGenericName(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = ((ParameterizedType) type);
            Type genericType = parameterizedType.getActualTypeArguments()[0];
            String typeName = genericType.getTypeName();
            if (!"?".equals(typeName)) {
                return getGenericName(genericType);
            }
        }
        return type.getTypeName().replace("<?>", "");
    }

}

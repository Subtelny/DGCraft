package pl.subtelny.utilities;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public final class ClassUtil {

    public static Class<?> getTypeVariableType(Class<?> subClass, TypeVariable<?> typeVariable) {
        Map<TypeVariable<?>, Type> subMap = new HashMap<>();
        Class<?> superClass;
        while ((superClass = subClass.getSuperclass()) != null) {

            Map<TypeVariable<?>, Type> superMap = new HashMap<>();
            Type superGeneric = subClass.getGenericSuperclass();
            if (superGeneric instanceof ParameterizedType) {

                TypeVariable<?>[] typeParams = superClass.getTypeParameters();
                Type[] actualTypeArgs = ((ParameterizedType) superGeneric).getActualTypeArguments();

                for (int i = 0; i < typeParams.length; i++) {
                    Type actualType = actualTypeArgs[i];
                    if (actualType instanceof TypeVariable) {
                        actualType = subMap.get(actualType);
                    }
                    if (typeVariable == typeParams[i]) return (Class<?>) actualType;
                    superMap.put(typeParams[i], actualType);
                }
            }
            subClass = superClass;
            subMap = superMap;
        }
        return null;
    }

    public static Class<?> getTypeParameterType(Class<?> subClass, Class<?> superClass, int typeParameterIndex) {
        return ClassUtil.getTypeVariableType(subClass, superClass.getTypeParameters()[typeParameterIndex]);
    }

}

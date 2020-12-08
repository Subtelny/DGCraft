package pl.subtelny.components.core;

import pl.subtelny.components.core.api.ComponentException;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentLoader {

    private final Map<Class, Object> components;

    public ComponentLoader(Map<Class, Object> components) {
        this.components = components;
    }

    public Object loadComponent(ComponentPrototype componentPrototype) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = componentPrototype.getConstructor();
        Object[] objects = findComponentObjectsForType(constructor.getGenericParameterTypes(), components);
        return constructor.newInstance(objects);
    }

    public Object loadComponent(Class clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ComponentPrototype componentPrototype = ComponentPrototype.from(clazz);
        return loadComponent(componentPrototype);
    }

    private Object[] findComponentObjectsForType(Type[] types, Map<Class, Object> initializedObjects) {
        List<Object> objects = new ArrayList<>();
        for (Type type : types) {
            List<Object> foundedObjects = initializedObjects.entrySet()
                    .stream()
                    .filter(classObjectEntry -> ComponentUtil.classMatchedToType(classObjectEntry.getKey(), type))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            if (foundedObjects.size() == 0) {
                throw ComponentException.of("Not found component object for type " + type.getTypeName());
            }

            if (ComponentUtil.isCollection(type)) {
                objects.add(CollectionUtil.streamToCollectionByType(type, foundedObjects.stream()));
            } else if (foundedObjects.size() > 1) {
                throw ComponentException.of("Found more than one component for type " + type.getTypeName());
            } else {
                objects.add(foundedObjects.get(0));
            }
        }
        return objects.toArray();
    }

}

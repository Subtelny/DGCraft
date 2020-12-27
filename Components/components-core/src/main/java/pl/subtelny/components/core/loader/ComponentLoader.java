package pl.subtelny.components.core.loader;

import pl.subtelny.components.core.ComponentUtil;
import pl.subtelny.components.core.api.ComponentException;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.components.core.prototype.ComponentPrototype;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentLoader {

    private final Map<Class, ComponentObjectInfo> components;

    public ComponentLoader(Map<Class, ComponentObjectInfo> components) {
        this.components = components;
    }

    public ComponentObjectInfo loadComponent(ComponentPrototype componentPrototype) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = componentPrototype.getConstructor();
        Object[] objects = findComponentObjectsForType(constructor.getGenericParameterTypes(), components);
        return new ComponentObjectInfo(componentPrototype.getComponentPlugin(), constructor.newInstance(objects));
    }

    public Object loadComponent(ComponentPlugin componentPlugin, Class clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ComponentPrototype componentPrototype = ComponentPrototype.from(clazz, componentPlugin);
        return loadComponent(componentPrototype);
    }

    private Object[] findComponentObjectsForType(Type[] types, Map<Class, ComponentObjectInfo> initializedObjects) {
        List<Object> objects = new ArrayList<>();
        for (Type type : types) {
            List<Object> foundedObjects = initializedObjects.entrySet()
                    .stream()
                    .filter(classObjectEntry -> ComponentUtil.classMatchedToType(classObjectEntry.getKey(), type))
                    .map(Map.Entry::getValue)
                    .map(ComponentObjectInfo::getObject)
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

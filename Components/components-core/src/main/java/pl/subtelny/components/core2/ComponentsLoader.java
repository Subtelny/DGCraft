package pl.subtelny.components.core2;

import pl.subtelny.components.core2.reflections.ComponentReflections;
import pl.subtelny.components.core2.reflections.ComponentScanner;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ComponentsLoader {

    private final List<ClassLoader> classLoaders;

    private final String PATH_TO_SCAN;

    public ComponentsLoader(List<ClassLoader> classLoaders, String path_to_scan) {
        this.classLoaders = classLoaders;
        PATH_TO_SCAN = path_to_scan;
    }

    public Map<Class, Object> loadComponents() {
        Set<ComponentPrototype> prototypes = getSortedComponentPrototypes();
        return initObjects(prototypes);
    }

    private Map<Class, Object> initObjects(Set<ComponentPrototype> componentPrototypes) {
        ComponentCyclicDependencyValidator validator = new ComponentCyclicDependencyValidator(componentPrototypes);
        Map<Class, Object> initializedObjects = new ConcurrentHashMap<>();
        for (ComponentPrototype componentPrototype : componentPrototypes) {
            validator.validate(componentPrototype);

            Constructor constructor = componentPrototype.getConstructor();
            try {
                Object[] parameters = findComponentObjectsForType(constructor.getGenericParameterTypes(), initializedObjects);
                Object object = constructor.newInstance(parameters);
                initializedObjects.put(componentPrototype.getClazz(), object);
            } catch (IllegalStateException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw ComponentException.of(String.format("Cannot create instance of component %s", componentPrototype.getClazz().getName()), e);
            }
        }
        return initializedObjects;
    }

    private Object[] findComponentObjectsForType(Type[] types, Map<Class, Object> initializedObjects) {
        List<Object> objects = new ArrayList<>();
        for (Type type : types) {
            List<Object> foundedObjects = initializedObjects.entrySet()
                    .stream()
                    .filter(classObjectEntry -> ComponentUtil.typeMatchedToClass(classObjectEntry.getKey(), type))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            if (foundedObjects.size() == 0) {
                throw ComponentException.of("Not found component object for type " + type.getTypeName());
            }

            if (ComponentUtil.isCollection(type)) {
                objects.add(CollectionUtil.streamToCollectionByType((Class<?>) type, foundedObjects.stream()));
            } else if (foundedObjects.size() > 1) {
                throw ComponentException.of("Found more than one component for type " + type.getTypeName());
            } else {
                objects.add(foundedObjects.get(0));
            }
        }
        return objects.toArray();
    }

    private Set<ComponentPrototype> getSortedComponentPrototypes() {
        Set<ComponentPrototype> componentPrototypes = getComponentPrototypes();
        ComponentComparator componentComparator = new ComponentComparator(componentPrototypes);
        return componentPrototypes.stream()
                .sorted(componentComparator)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<ComponentPrototype> getComponentPrototypes() {
        return getComponentTypes().stream()
                .map(ComponentPrototype::from)
                .collect(Collectors.toSet());
    }

    private Set<Class<?>> getComponentTypes() {
        List<Object> objects = new ArrayList<>(classLoaders);
        objects.add(PATH_TO_SCAN);
        objects.add(new ComponentScanner());
        return new ComponentReflections(objects).getComponentTypes();
    }

}

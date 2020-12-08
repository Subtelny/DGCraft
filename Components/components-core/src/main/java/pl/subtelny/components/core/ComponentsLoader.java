package pl.subtelny.components.core;

import pl.subtelny.components.core.api.ComponentException;
import pl.subtelny.components.core.reflections.ComponentReflections;
import pl.subtelny.components.core.reflections.ComponentScanner;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class ComponentsLoader {

    private final List<ClassLoader> classLoaders;

    private final String PATH_TO_SCAN;

    public ComponentsLoader(List<ClassLoader> classLoaders, String path_to_scan) {
        this.classLoaders = classLoaders;
        PATH_TO_SCAN = path_to_scan;
    }

    public Map<Class, Object> loadComponents() {
        Set<ComponentPrototypeOrder> prototypes = getComponentPrototypeOrders();
        return initObjects(prototypes);
    }

    private Map<Class, Object> initObjects(Set<ComponentPrototypeOrder> componentPrototypes) {
        Map<Class, Object> initializedObjects = new HashMap<>();
        return initObjects(initializedObjects, componentPrototypes);
    }

    private Map<Class, Object> initObjects(Map<Class, Object> initializedObjects, Set<ComponentPrototypeOrder> componentsToInitialize) {
        Iterator<ComponentPrototypeOrder> iterator = componentsToInitialize.iterator();
        while (iterator.hasNext()) {
            ComponentPrototypeOrder next = iterator.next();
            ComponentPrototype prototype = next.getPrototype();
            Set<ComponentPrototype> hasToBeInitializedBefore = next.getPrototypesHasToBeInitializedBefore();

            boolean canBeInitialized = hasToBeInitializedBefore.stream()
                    .map(ComponentPrototype::getClazz)
                    .allMatch(initializedObjects::containsKey);

            if (canBeInitialized) {
                ComponentLoader componentLoader = new ComponentLoader(initializedObjects);
                try {
                    Object object = componentLoader.loadComponent(prototype);
                    initializedObjects.put(prototype.getClazz(), object);
                    iterator.remove();
                } catch (ComponentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw ComponentException.of(String.format("Cannot create instance of component %s", prototype.getClazz().getName()), e);
                }
            }
        }
        if (componentsToInitialize.size() > 0) {
            return initObjects(initializedObjects, componentsToInitialize);
        }
        return initializedObjects;
    }

    private Set<ComponentPrototypeOrder> getComponentPrototypeOrders() {
        Set<ComponentPrototype> componentPrototypes = getComponentPrototypes();

        ComponentCyclicDependencyValidator validator = new ComponentCyclicDependencyValidator(componentPrototypes);
        componentPrototypes.forEach(validator::validate);

        return componentPrototypes.stream()
                .map(componentPrototype -> new ComponentPrototypeOrder(componentPrototype, componentPrototypes))
                .collect(Collectors.toSet());
    }

    private Set<ComponentPrototype> getComponentPrototypes() {
        return getComponentTypes().stream()
                .filter(aClass -> !aClass.isInterface())
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

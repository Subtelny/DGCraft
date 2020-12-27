package pl.subtelny.components.core.loader;

import pl.subtelny.components.core.ComponentClassLoaderInfo;
import pl.subtelny.components.core.api.ComponentException;
import pl.subtelny.components.core.prototype.ComponentPrototype;
import pl.subtelny.components.core.prototype.ComponentPrototypeOrder;
import pl.subtelny.components.core.reflections.ComponentReflections;
import pl.subtelny.components.core.reflections.ComponentScanner;
import pl.subtelny.components.core.validation.ComponentCyclicDependencyValidator;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ComponentsLoader {

    private final List<ComponentClassLoaderInfo> classLoaders;

    private final String PATH_TO_SCAN;

    public ComponentsLoader(List<ComponentClassLoaderInfo> classLoaders, String path_to_scan) {
        this.classLoaders = classLoaders;
        PATH_TO_SCAN = path_to_scan;
    }

    public Map<Class, ComponentObjectInfo> loadComponents() {
        Set<ComponentPrototypeOrder> prototypes = getComponentPrototypeOrders();
        return initObjects(prototypes);
    }

    private Map<Class, ComponentObjectInfo> initObjects(Set<ComponentPrototypeOrder> componentPrototypes) {
        Map<Class, ComponentObjectInfo> initializedObjects = new HashMap<>();
        return initObjects(initializedObjects, componentPrototypes);
    }

    private Map<Class, ComponentObjectInfo> initObjects(Map<Class, ComponentObjectInfo> initializedObjects,
                                                        Set<ComponentPrototypeOrder> componentsToInitialize) {
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
                    ComponentObjectInfo objectInfo = componentLoader.loadComponent(prototype);
                    initializedObjects.put(prototype.getClazz(), objectInfo);
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
        Set<ComponentPrototype> componentPrototypes = classLoaders.stream()
                .flatMap(componentClassLoaderInfo -> getComponentPrototypes(componentClassLoaderInfo).stream())
                .collect(Collectors.toSet());

        ComponentCyclicDependencyValidator validator = new ComponentCyclicDependencyValidator(componentPrototypes);
        componentPrototypes.forEach(validator::validate);

        return componentPrototypes.stream()
                .map(componentPrototype -> new ComponentPrototypeOrder(componentPrototype, componentPrototypes))
                .collect(Collectors.toSet());
    }

    private Set<ComponentPrototype> getComponentPrototypes(ComponentClassLoaderInfo classLoaderInfo) {
        return getComponentTypes(classLoaderInfo).stream()
                .filter(aClass -> !aClass.isInterface())
                .map(aClass -> ComponentPrototype.from(aClass, classLoaderInfo.getComponentPlugin()))
                .collect(Collectors.toSet());
    }

    private Set<Class<?>> getComponentTypes(ComponentClassLoaderInfo classLoaderInfo) {
        List<Object> objects = new ArrayList<>();
        objects.add(classLoaderInfo.getClassLoader());
        objects.add(PATH_TO_SCAN);
        objects.add(new ComponentScanner());
        return new ComponentReflections(objects).getComponentTypes();
    }

}

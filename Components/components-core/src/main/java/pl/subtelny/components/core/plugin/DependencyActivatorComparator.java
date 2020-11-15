package pl.subtelny.components.core.plugin;

import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;

import java.util.Arrays;
import java.util.Comparator;

public class DependencyActivatorComparator implements Comparator<DependencyActivator> {

    @Override
    public int compare(DependencyActivator dependencyActivator, DependencyActivator t1) {
        int priority = findPriority(dependencyActivator).getPriority();
        int prioritySecond = findPriority(t1).getPriority();
        return Integer.compare(priority, prioritySecond);
    }

    private DependencyActivatorPriority.Priority findPriority(DependencyActivator dependencyActivator) {
        Class<? extends DependencyActivator> aClass = dependencyActivator.getClass();
        if (aClass.isAnnotationPresent(DependencyActivatorPriority.class)) {
            return aClass.getAnnotation(DependencyActivatorPriority.class).priority();
        }
        return Arrays.stream(aClass.getInterfaces())
                .filter(aClass1 -> aClass1.isAnnotationPresent(DependencyActivatorPriority.class))
                .map(aClass1 -> aClass1.getAnnotation(DependencyActivatorPriority.class).priority())
                .findAny()
                .orElse(DependencyActivatorPriority.Priority.MEDIUM);
    }

}

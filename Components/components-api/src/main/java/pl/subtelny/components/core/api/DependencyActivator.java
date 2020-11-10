package pl.subtelny.components.core.api;

@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.MEDIUM)
public interface DependencyActivator {

    void activate();

}

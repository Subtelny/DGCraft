package pl.subtelny.components.core.api;

import pl.subtelny.components.core.api.plugin.ComponentPlugin;

@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.MEDIUM)
public interface DependencyActivator {

    void activate(ComponentPlugin componentPlugin);

}

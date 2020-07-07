package pl.subtelny.components.core.api;

import org.bukkit.plugin.Plugin;

@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.MEDIUM)
public interface DependencyActivator {

    void activate(Plugin plugin);

}

package pl.subtelny.components.core2.plugin;

import java.util.Objects;

public class DependenciesLoadResults {

    private final int commands;

    private final int listeners;

    private final int dependencyActivators;

    private final int componentPlugins;

    public DependenciesLoadResults(int commands, int listeners, int dependencyActivators, int componentPlugins) {
        this.commands = commands;
        this.listeners = listeners;
        this.dependencyActivators = dependencyActivators;
        this.componentPlugins = componentPlugins;
    }

    public int getCommands() {
        return commands;
    }

    public int getListeners() {
        return listeners;
    }

    public int getDependencyActivators() {
        return dependencyActivators;
    }

    public int getComponentPlugins() {
        return componentPlugins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependenciesLoadResults that = (DependenciesLoadResults) o;
        return commands == that.commands &&
                listeners == that.listeners &&
                dependencyActivators == that.dependencyActivators &&
                componentPlugins == that.componentPlugins;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commands, listeners, dependencyActivators, componentPlugins);
    }
}

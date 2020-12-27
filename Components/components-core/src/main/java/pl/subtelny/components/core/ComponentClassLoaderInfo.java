package pl.subtelny.components.core;

import pl.subtelny.components.core.api.plugin.ComponentPlugin;

import java.util.Objects;

public class ComponentClassLoaderInfo {

    private final ComponentPlugin componentPlugin;

    private final ClassLoader classLoader;

    public ComponentClassLoaderInfo(ComponentPlugin componentPlugin, ClassLoader classLoader) {
        this.componentPlugin = componentPlugin;
        this.classLoader = classLoader;
    }

    public ComponentPlugin getComponentPlugin() {
        return componentPlugin;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentClassLoaderInfo that = (ComponentClassLoaderInfo) o;
        return Objects.equals(componentPlugin, that.componentPlugin) &&
                Objects.equals(classLoader, that.classLoader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentPlugin, classLoader);
    }
}

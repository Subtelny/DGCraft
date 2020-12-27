package pl.subtelny.components.core.loader;

import pl.subtelny.components.core.api.plugin.ComponentPlugin;

import java.util.Objects;

public class ComponentObjectInfo {

    private final ComponentPlugin componentPlugin;

    private final Object object;

    public ComponentObjectInfo(ComponentPlugin componentPlugin, Object object) {
        this.componentPlugin = componentPlugin;
        this.object = object;
    }

    public ComponentPlugin getComponentPlugin() {
        return componentPlugin;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentObjectInfo that = (ComponentObjectInfo) o;
        return Objects.equals(componentPlugin, that.componentPlugin) &&
                Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentPlugin, object);
    }
}

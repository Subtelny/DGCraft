package pl.subtelny.components.core.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PluginData {

    private final List<String> paths;

    private final ClassLoader classLoader;

    public PluginData(List<String> paths, ClassLoader classLoader) {
        this.paths = paths;
        this.classLoader = classLoader;
    }

    public List<String> getPaths() {
        return new ArrayList<>(paths);
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginData that = (PluginData) o;
        return Objects.equals(paths, that.paths) &&
                Objects.equals(classLoader, that.classLoader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paths, classLoader);
    }
}

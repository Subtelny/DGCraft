package pl.subtelny.components.core.api;

import java.util.List;
import java.util.Objects;

public class PluginInformation {

    private final List<String> paths;

    private final ClassLoader classLoader;

    public PluginInformation(List<String> paths, ClassLoader classLoader) {
        this.paths = paths;
        this.classLoader = classLoader;
    }

    public List<String> getPaths() {
        return paths;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginInformation that = (PluginInformation) o;
        return Objects.equals(paths, that.paths) &&
                Objects.equals(classLoader, that.classLoader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paths, classLoader);
    }
}

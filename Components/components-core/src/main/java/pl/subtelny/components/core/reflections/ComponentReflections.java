package pl.subtelny.components.core.reflections;

import com.google.common.collect.Sets;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;

public class ComponentReflections extends Reflections {

    private static final String COMPONENT_SCANNER_INDEX = ComponentScanner.class.getSimpleName();

    public ComponentReflections(List<Object> objects) {
        super(objects);
    }

    public Set<Class<?>> getComponentTypes() {
        Set<String> classes = getStore().get(ComponentScanner.class, COMPONENT_SCANNER_INDEX);
        //Multimap<String, String> componentsStore = getStore().get(COMPONENT_SCANNER_INDEX);
        //Collection<String> values = componentsStore.values();
        return Sets.newHashSet(ReflectionUtils.forNames(classes, getConfiguration().getClassLoaders()));
    }

}

package pl.subtelny.components.core;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;

import java.util.List;

@Component
public class ComponentProviderImpl implements ComponentProvider {

    @Override
    public <T> T createComponent(ComponentPlugin componentPlugin, Class<T> clazz) {
        return ComponentContext.getContext().createComponent(componentPlugin, clazz);
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        return ComponentContext.getContext().getComponentsStorage().getComponent(clazz);
    }

    @Override
    public <T> List<T> getComponents(Class<T> clazz) {
        return ComponentContext.getContext().getComponentsStorage().getComponents(clazz);
    }

}

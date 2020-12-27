package pl.subtelny.components.core.prototype;

import pl.subtelny.components.core.ComponentContext;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.ComponentProvider;

import java.util.List;

@Component
public class ComponentProviderImpl implements ComponentProvider {

    @Override
    public <T> T createComponent(Class<T> clazz) {
        return ComponentContext.getContext().createComponent(null, clazz);
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

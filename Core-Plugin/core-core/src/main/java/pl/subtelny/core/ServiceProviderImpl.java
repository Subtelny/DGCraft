package pl.subtelny.core;

import pl.subtelny.components.core.ComponentsContext;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.ServiceProvider;

@Component
public class ServiceProviderImpl implements ServiceProvider {

    @Override
    public <T> T getBean(Class<T> clazz) {
        return ComponentsContext.getBean(clazz);
    }

}

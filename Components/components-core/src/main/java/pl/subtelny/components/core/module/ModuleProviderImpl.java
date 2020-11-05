package pl.subtelny.components.core.module;

import pl.subtelny.components.core.ComponentsContext;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.module.BeanProvider;

import java.util.Optional;

@Component
public class ModuleProviderImpl implements BeanProvider {

    @Override
    public <T> Optional<T> getOptionalBean(Class<T> clazz) {
        T bean = ComponentsContext.getBean(clazz);
        return Optional.ofNullable(bean);
    }

    @Override
    public <T> T getRequiredBean(Class<T> clazz) {
        return ComponentsContext.getBean(clazz);
    }

}

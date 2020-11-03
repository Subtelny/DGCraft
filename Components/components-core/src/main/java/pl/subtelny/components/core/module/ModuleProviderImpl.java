package pl.subtelny.components.core.module;

import pl.subtelny.components.core.ComponentsContext;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.module.ModuleProvider;

import java.util.Optional;

@Component
public class ModuleProviderImpl implements ModuleProvider {

    @Override
    public <T> Optional<T> getOptionalModule(Class<T> clazz) {
        T bean = ComponentsContext.getBean(clazz);
        return Optional.ofNullable(bean);
    }

    @Override
    public <T> T getRequiredModule(Class<T> clazz) {
        return ComponentsContext.getBean(clazz);
    }

}

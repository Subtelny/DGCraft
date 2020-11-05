package pl.subtelny.components.core.api.module;

import java.util.Optional;

public interface BeanProvider {

    <T> Optional<T> getOptionalBean(Class<T> clazz);

    <T> T getRequiredBean(Class<T> clazz);

}

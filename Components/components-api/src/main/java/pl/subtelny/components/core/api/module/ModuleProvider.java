package pl.subtelny.components.core.api.module;

import java.util.Optional;

public interface ModuleProvider {

    <T> Optional<T> getOptionalModule(Class<T> clazz);

    <T> T getRequiredModule(Class<T> clazz);

}

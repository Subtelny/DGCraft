package pl.subtelny.components.core.api;

import java.util.List;

public interface ComponentProvider {

    <T> T createComponent(Class<T> clazz);

    <T> T getComponent(Class<T> clazz);

    <T> List<T> getComponents(Class<T> clazz);

}

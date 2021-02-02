package pl.subtelny.components.core.api;

import pl.subtelny.components.core.api.plugin.ComponentPlugin;

import java.util.List;

public interface ComponentProvider {

    <T> T createComponent(ComponentPlugin componentPlugin, Class<T> clazz);

    <T> T getComponent(Class<T> clazz);

    <T> List<T> getComponents(Class<T> clazz);

}

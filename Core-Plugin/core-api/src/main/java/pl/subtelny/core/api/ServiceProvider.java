package pl.subtelny.core.api;

public interface ServiceProvider {

    <T> T getBean(Class<T> clazz);

}

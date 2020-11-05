package pl.subtelny.core.api.repository;

public interface Updater<T, R> {

    R performAction(T entity);

}

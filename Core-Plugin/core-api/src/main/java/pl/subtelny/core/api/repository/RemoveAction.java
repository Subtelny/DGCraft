package pl.subtelny.core.api.repository;

public interface RemoveAction<T> {

    void perform(T t);

}

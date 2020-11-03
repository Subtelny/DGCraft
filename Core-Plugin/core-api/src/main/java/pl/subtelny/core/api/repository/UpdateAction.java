package pl.subtelny.core.api.repository;

public interface UpdateAction<T, R> {

    R perform(T anemia);

}

package pl.subtelny.executor;

public interface Job<T> {

    T execute();

}

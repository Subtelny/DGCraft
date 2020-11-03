package pl.subtelny.utilities;

@FunctionalInterface
public interface Callback<T> {

    void done(T t);

}

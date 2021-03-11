package pl.subtelny.utilities.thread;

@FunctionalInterface
public interface CallbackReturn<T, E> {

    T done(E e);

}

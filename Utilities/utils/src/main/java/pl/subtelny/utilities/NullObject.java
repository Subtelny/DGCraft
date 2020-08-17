package pl.subtelny.utilities;

import java.util.Optional;

public interface NullObject<T> {

    Optional<T> get();

    static <V> NullObject<V> empty() {
        return new EmptyNullObject<>();
    }

    static <V> NullObject<V> of(V value) {
        return new ExistNullObject<>(value);
    }

}

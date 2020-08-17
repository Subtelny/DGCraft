package pl.subtelny.utilities;

import java.util.Optional;

public class EmptyNullObject<T> implements NullObject<T> {

    @Override
    public Optional<T> get() {
        return Optional.empty();
    }

}

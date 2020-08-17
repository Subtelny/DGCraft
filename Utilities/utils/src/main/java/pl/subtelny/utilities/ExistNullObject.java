package pl.subtelny.utilities;

import com.google.common.base.Preconditions;

import java.util.Objects;
import java.util.Optional;

public class ExistNullObject<T> implements NullObject<T> {

    private final T value;

    public ExistNullObject(T value) {
        this.value = Preconditions.checkNotNull(value);
    }

    @Override
    public Optional<T> get() {
        return Optional.of(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExistNullObject<?> that = (ExistNullObject<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

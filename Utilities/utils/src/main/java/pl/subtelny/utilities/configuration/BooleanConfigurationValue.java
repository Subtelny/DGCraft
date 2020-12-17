package pl.subtelny.utilities.configuration;

import java.util.Objects;

public class BooleanConfigurationValue implements ConfigurationValue<Boolean> {

    private final boolean value;

    public BooleanConfigurationValue(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean get() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanConfigurationValue that = (BooleanConfigurationValue) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

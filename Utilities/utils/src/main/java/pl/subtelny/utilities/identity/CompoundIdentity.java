package pl.subtelny.utilities.identity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class CompoundIdentity implements Serializable {

    protected static final String SEPARATOR = "@";

    private final String[] values;

    public CompoundIdentity(String values) {
        this.values = values.split(SEPARATOR);
    }

    public static String values(String... values) {
        return Arrays.stream(values).map(String::valueOf).collect(Collectors.joining(SEPARATOR));
    }

    public String getAtPosition(int position) {
        return values[position];
    }

    public String getInternal() {
        return String.join(SEPARATOR, values);
    }

    @Override
    public String toString() {
        return String.join(SEPARATOR, values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundIdentity that = (CompoundIdentity) o;
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}

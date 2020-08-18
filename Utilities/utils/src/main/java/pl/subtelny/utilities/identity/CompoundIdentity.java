package pl.subtelny.utilities.identity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class CompoundIdentity implements Serializable {

    private static final String SEPARATOR = "@";

    private final String[] values;

    public CompoundIdentity(String values) {
        this.values = values.split(SEPARATOR);
    }

    public static String values(Object... objects) {
        return Arrays.stream(objects).map(String::valueOf).collect(Collectors.joining(SEPARATOR));
    }

    public String getAtPosition(int position) {
        return values[position];
    }

    @Override
    public String toString() {
        return "CompoundIdentity{" +
                "values=" + Arrays.toString(values) +
                '}';
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
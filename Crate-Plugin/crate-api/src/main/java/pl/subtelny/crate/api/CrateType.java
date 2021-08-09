package pl.subtelny.crate.api;

import java.util.Objects;

public class CrateType {

    private final String value;

    public CrateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrateType crateType = (CrateType) o;
        return Objects.equals(value, crateType.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

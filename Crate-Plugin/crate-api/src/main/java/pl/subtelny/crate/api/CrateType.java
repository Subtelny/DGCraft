package pl.subtelny.crate.api;

import java.util.Objects;

public class CrateType {

    private final String type;

    private CrateType(String type) {
        this.type = type;
    }

    public static CrateType of(String type) {
        return new CrateType(type);
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrateType crateType = (CrateType) o;
        return Objects.equals(type, crateType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

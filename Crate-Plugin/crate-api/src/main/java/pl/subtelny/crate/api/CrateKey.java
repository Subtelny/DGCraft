package pl.subtelny.crate.api;

import java.util.Objects;

public class CrateKey {

    private final String identity;

    private CrateKey(String identity) {
        this.identity = identity;
    }

    public static CrateKey of(String prefix, String id) {
        return of(prefix + "-" + id);
    }

    public static CrateKey of(String id) {
        return new CrateKey(id);
    }

    public String getIdentity() {
        return identity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrateKey crateKey = (CrateKey) o;
        return Objects.equals(identity, crateKey.identity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity);
    }
}

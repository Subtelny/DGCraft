package pl.subtelny.islands.island;

import java.util.Objects;
import java.util.Optional;

public class IslandCreateRequest {

    private final IslandMember owner;

    public IslandCreateRequest(IslandMember owner) {
        this.owner = owner;
    }

    public Optional<IslandMember> getOwner() {
        return Optional.ofNullable(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandCreateRequest request = (IslandCreateRequest) o;
        return Objects.equals(owner, request.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner);
    }
}

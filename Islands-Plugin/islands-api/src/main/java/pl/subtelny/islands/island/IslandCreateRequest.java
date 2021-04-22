package pl.subtelny.islands.island;

import java.util.Objects;
import java.util.Optional;

public class IslandCreateRequest {

    private final IslandMember owner;

    private final IslandType islandType;

    public IslandCreateRequest(IslandMember owner, IslandType islandType) {
        this.owner = owner;
        this.islandType = islandType;
    }

    public static Builder newBuilder(IslandType islandType) {
        return new Builder(islandType);
    }

    public Optional<IslandMember> getOwner() {
        return Optional.ofNullable(owner);
    }

    public IslandType getIslandType() {
        return islandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandCreateRequest request = (IslandCreateRequest) o;
        return Objects.equals(owner, request.owner) &&
                Objects.equals(islandType, request.islandType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, islandType);
    }

    public static class Builder {

        private final IslandType islandType;
        private IslandMember owner;

        public Builder(IslandType islandType) {
            this.islandType = islandType;
        }

        public Builder setOwner(IslandMember owner) {
            this.owner = owner;
            return this;
        }

        public IslandCreateRequest build() {
            return new IslandCreateRequest(owner, islandType);
        }

    }
}

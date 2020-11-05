package pl.subtelny.islands.island.membership.repository.load;

import org.apache.commons.lang.Validate;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.Objects;
import java.util.Optional;

public class IslandMembershipLoadRequest {

    private final IslandId islandId;

    private final IslandMemberId islandMemberId;

    private IslandMembershipLoadRequest(IslandId islandId, IslandMemberId islandMemberId) {
        Validate.isTrue(islandId != null || islandMemberId != null, "Atleast one parameter cannot be null");
        this.islandId = islandId;
        this.islandMemberId = islandMemberId;
    }

    public Optional<IslandId> getIslandId() {
        return Optional.ofNullable(islandId);
    }

    public Optional<IslandMemberId> getIslandMemberId() {
        return Optional.ofNullable(islandMemberId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private IslandId islandId;

        private IslandMemberId islandMemberId;

        public Builder where(IslandId islandId) {
            this.islandId = islandId;
            return this;
        }

        public Builder where(IslandMemberId islandMemberId) {
            this.islandMemberId = islandMemberId;
            return this;
        }

        public IslandMembershipLoadRequest build() {
            return new IslandMembershipLoadRequest(islandId, islandMemberId);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipLoadRequest that = (IslandMembershipLoadRequest) o;
        return Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandMemberId, that.islandMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandMemberId);
    }
}

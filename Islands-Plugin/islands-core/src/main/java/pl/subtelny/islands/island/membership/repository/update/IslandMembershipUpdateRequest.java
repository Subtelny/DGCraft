package pl.subtelny.islands.island.membership.repository.update;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.Objects;

public class IslandMembershipUpdateRequest {

    private final IslandId islandId;

    private final IslandMemberId islandMemberId;

    private final boolean owner;

    private IslandMembershipUpdateRequest(IslandId islandId, IslandMemberId islandMemberId, boolean owner) {
        this.islandId = islandId;
        this.islandMemberId = islandMemberId;
        this.owner = owner;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public IslandMemberId getIslandMemberId() {
        return islandMemberId;
    }

    public boolean isOwner() {
        return owner;
    }

    public static IslandMembershipUpdateRequest request(IslandId islandId, IslandMemberId islandMemberId, boolean owner) {
        return new IslandMembershipUpdateRequest(islandId, islandMemberId, owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipUpdateRequest that = (IslandMembershipUpdateRequest) o;
        return owner == that.owner &&
                Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandMemberId, that.islandMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandMemberId, owner);
    }
}

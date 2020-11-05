package pl.subtelny.islands.island.membership.repository.remove;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import java.util.Objects;

public class IslandMembershipRemoveRequest {

    private final IslandId islandId;

    private final IslandMemberId islandMemberId;

    private IslandMembershipRemoveRequest(IslandId islandId, IslandMemberId islandMemberId) {
        this.islandId = islandId;
        this.islandMemberId = islandMemberId;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public IslandMemberId getIslandMemberId() {
        return islandMemberId;
    }

    public static IslandMembershipRemoveRequest request(IslandId islandId, IslandMemberId islandMemberId) {
        return new IslandMembershipRemoveRequest(islandId, islandMemberId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipRemoveRequest that = (IslandMembershipRemoveRequest) o;
        return Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandMemberId, that.islandMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandMemberId);
    }
}

package pl.subtelny.islands.islandmembership.repository.updater;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.Objects;

public class IslandMembershipUpdateRequest {

    private final IslandId islandId;

    private final IslandMemberId islandMemberId;

    private IslandMembershipUpdateRequest(IslandId islandId, IslandMemberId islandMemberId) {
        this.islandId = islandId;
        this.islandMemberId = islandMemberId;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public IslandMemberId getIslandMemberId() {
        return islandMemberId;
    }

    public static IslandMembershipUpdateRequest request(IslandId islandId, IslandMemberId islandMemberId) {
        return new IslandMembershipUpdateRequest(islandId, islandMemberId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipUpdateRequest that = (IslandMembershipUpdateRequest) o;
        return Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandMemberId, that.islandMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandMemberId);
    }
}

package pl.subtelny.islands.island.membership.repository;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.Objects;

public class IslandMembershipAnemia {

    private IslandId islandId;

    private IslandMemberId islandMemberId;

    private boolean owner;

    public IslandMembershipAnemia(IslandId islandId, IslandMemberId islandMemberId, boolean owner) {
        this.islandId = islandId;
        this.islandMemberId = islandMemberId;
        this.owner = owner;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public void setIslandId(IslandId islandId) {
        this.islandId = islandId;
    }

    public IslandMemberId getIslandMemberId() {
        return islandMemberId;
    }

    public void setIslandMemberId(IslandMemberId islandMemberId) {
        this.islandMemberId = islandMemberId;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipAnemia that = (IslandMembershipAnemia) o;
        return owner == that.owner &&
                Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandMemberId, that.islandMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandMemberId, owner);
    }
}

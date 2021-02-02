package pl.subtelny.islands.island.membership.model;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.Objects;

public class IslandMembership {

    private final IslandMemberId islandMemberId;

    private final IslandId islandId;

    private final boolean owner;

    public IslandMembership(IslandMemberId islandMemberId, IslandId islandId, boolean owner) {
        this.islandMemberId = islandMemberId;
        this.islandId = islandId;
        this.owner = owner;
    }

    public static IslandMembership member(IslandMemberId islandMemberId, IslandId islandId) {
        return new IslandMembership(islandMemberId, islandId, false);
    }

    public static IslandMembership owner(IslandMemberId islandMemberId, IslandId islandId) {
        return new IslandMembership(islandMemberId, islandId, true);
    }

    public IslandMemberId getIslandMemberId() {
        return islandMemberId;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public boolean isOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembership that = (IslandMembership) o;
        return owner == that.owner &&
                Objects.equals(islandMemberId, that.islandMemberId) &&
                Objects.equals(islandId, that.islandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandMemberId, islandId, owner);
    }
}

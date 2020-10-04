package pl.subtelny.islands.islandmembership.repository.loader;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.Objects;

public class IslandMembership {

    private final IslandId islandId;

    private final IslandMemberId islandMemberId;

    private final boolean owner;

    public IslandMembership(IslandId islandId, IslandMemberId islandMemberId, boolean owner) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembership that = (IslandMembership) o;
        return owner == that.owner &&
                Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandMemberId, that.islandMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandMemberId, owner);
    }
}

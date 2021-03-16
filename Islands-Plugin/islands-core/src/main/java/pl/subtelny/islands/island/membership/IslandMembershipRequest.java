package pl.subtelny.islands.island.membership;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;

import java.util.Objects;

public class IslandMembershipRequest {

    private final IslandMember islandMember;

    private final IslandId islandId;

    private final boolean join;

    private IslandMembershipRequest(IslandMember islandMember, IslandId islandId, boolean join) {
        this.islandMember = islandMember;
        this.islandId = islandId;
        this.join = join;
    }

    public static IslandMembershipRequest leave(IslandMember islandMember, IslandId islandId) {
        return new IslandMembershipRequest(islandMember, islandId, false);
    }

    public static IslandMembershipRequest join(IslandMember islandMember, IslandId islandId) {
        return new IslandMembershipRequest(islandMember, islandId, true);
    }

    public IslandMember getIslandMember() {
        return islandMember;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public boolean isJoin() {
        return join;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipRequest that = (IslandMembershipRequest) o;
        return join == that.join && Objects.equals(islandMember, that.islandMember) && Objects.equals(islandId, that.islandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandMember, islandId, join);
    }
}

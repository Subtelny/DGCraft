package pl.subtelny.islands.island.model;

import pl.subtelny.islands.island.IslandMember;

import java.util.Objects;

public final class IslandMemberChangedRequest {

    private final IslandMember islandMember;

    private final Type type;

    private final boolean owner;

    private IslandMemberChangedRequest(IslandMember islandMember, Type type) {
        this.islandMember = islandMember;
        this.type = type;
        this.owner = false;
    }

    private IslandMemberChangedRequest(IslandMember islandMember, Type type, boolean owner) {
        this.islandMember = islandMember;
        this.type = type;
        this.owner = owner;
    }

    public static IslandMemberChangedRequest added(IslandMember islandMember) {
        return new IslandMemberChangedRequest(islandMember, Type.ADDED, false);
    }

    public static IslandMemberChangedRequest removed(IslandMember islandMember) {
        return new IslandMemberChangedRequest(islandMember, Type.REMOVED);
    }

    public IslandMember getIslandMember() {
        return islandMember;
    }

    public Type getType() {
        return type;
    }

    public boolean isOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMemberChangedRequest that = (IslandMemberChangedRequest) o;
        return owner == that.owner &&
                Objects.equals(islandMember, that.islandMember) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandMember);
    }

    public enum Type {

        ADDED,
        REMOVED

    }

}

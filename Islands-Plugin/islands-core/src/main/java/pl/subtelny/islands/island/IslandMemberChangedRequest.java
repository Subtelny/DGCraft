package pl.subtelny.islands.island;

import java.util.Objects;

public final class IslandMemberChangedRequest {

    private final IslandMemberId islandMember;

    private final Type type;

    private final boolean owner;

    private IslandMemberChangedRequest(IslandMemberId islandMember, Type type) {
        this.islandMember = islandMember;
        this.type = type;
        this.owner = false;
    }

    private IslandMemberChangedRequest(IslandMemberId islandMember, Type type, boolean owner) {
        this.islandMember = islandMember;
        this.type = type;
        this.owner = owner;
    }

    public static IslandMemberChangedRequest update(IslandMemberId islandMember, boolean owner) {
        return new IslandMemberChangedRequest(islandMember, Type.UPDATE_OWNER, owner);
    }

    public static IslandMemberChangedRequest added(IslandMember islandMember) {
        return new IslandMemberChangedRequest(islandMember.getId(), Type.ADDED, false);
    }

    public static IslandMemberChangedRequest removed(IslandMember islandMember) {
        return new IslandMemberChangedRequest(islandMember.getId(), Type.REMOVED);
    }

    public IslandMemberId getIslandMember() {
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
        return Objects.equals(islandMember, that.islandMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandMember);
    }

    public enum Type {

        ADDED,
        REMOVED,
        UPDATE_OWNER

    }

}

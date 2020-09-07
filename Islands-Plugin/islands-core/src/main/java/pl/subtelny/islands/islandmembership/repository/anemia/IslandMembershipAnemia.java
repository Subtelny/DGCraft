package pl.subtelny.islands.islandmembership.repository.anemia;

import java.util.Objects;

public class IslandMembershipAnemia {

    private Integer islandId;

    private String islandMemberId;

    private boolean owner;

    public IslandMembershipAnemia(Integer islandId, String islandMemberId, boolean owner) {
        this.islandId = islandId;
        this.islandMemberId = islandMemberId;
        this.owner = owner;
    }

    public Integer getIslandId() {
        return islandId;
    }

    public String getIslandMemberId() {
        return islandMemberId;
    }

    public boolean isOwner() {
        return owner;
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

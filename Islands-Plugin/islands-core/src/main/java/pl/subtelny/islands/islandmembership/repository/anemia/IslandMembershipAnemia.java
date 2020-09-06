package pl.subtelny.islands.islandmembership.repository.anemia;

import java.util.Objects;

public class IslandMembershipAnemia {

    private Integer islandId;

    private String islandMemberId;

    public IslandMembershipAnemia(Integer islandId, String islandMemberId) {
        this.islandId = islandId;
        this.islandMemberId = islandMemberId;
    }

    public Integer getIslandId() {
        return islandId;
    }

    public String getIslandMemberId() {
        return islandMemberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipAnemia that = (IslandMembershipAnemia) o;
        return Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandMemberId, that.islandMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandMemberId);
    }
}

package pl.subtelny.islands.islandmembership.anemia;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandMemberRank;

import java.util.Objects;

public class IslandMembershipAnemia {

    private final IslandMemberId islandMemberId;

    private final IslandId islandId;

    private final IslandMemberRank rank;

    public IslandMembershipAnemia(IslandMemberId islandMemberId, IslandId islandId,  IslandMemberRank rank) {
        this.islandMemberId = islandMemberId;
        this.islandId = islandId;
        this.rank = rank;
    }

    public IslandMemberId getIslandMemberId() {
        return islandMemberId;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public IslandMemberRank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipAnemia that = (IslandMembershipAnemia) o;
        return Objects.equals(islandMemberId, that.islandMemberId) &&
                Objects.equals(islandId, that.islandId) &&
                Objects.equals(rank, that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandMemberId, islandId, rank);
    }
}

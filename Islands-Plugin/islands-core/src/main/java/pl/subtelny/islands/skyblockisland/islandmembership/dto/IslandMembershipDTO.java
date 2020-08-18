package pl.subtelny.islands.skyblockisland.islandmembership.dto;

import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.skyblockisland.model.MembershipType;

import java.util.Objects;

public class IslandMembershipDTO {

    private final IslanderId islanderId;

    private final IslandId islandId;

    private final MembershipType membershipType;

    public IslandMembershipDTO(IslanderId islanderId, IslandId islandId, MembershipType membershipType) {
        this.islanderId = islanderId;
        this.islandId = islandId;
        this.membershipType = membershipType;
    }

    public IslanderId getIslanderId() {
        return islanderId;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipDTO that = (IslandMembershipDTO) o;
        return Objects.equals(islanderId, that.islanderId) &&
                Objects.equals(islandId, that.islandId) &&
                membershipType == that.membershipType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(islanderId, islandId, membershipType);
    }
}
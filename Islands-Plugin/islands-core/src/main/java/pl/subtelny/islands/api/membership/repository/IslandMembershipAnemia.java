package pl.subtelny.islands.api.membership.repository;

import org.jooq.DSLContext;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.generated.tables.tables.records.IslandMembershipsRecord;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMemberId;
import pl.subtelny.islands.api.membership.model.IslandMembership;

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

    public IslandMembershipsRecord toRecord(DSLContext context) {
        IslandMembershipsRecord record = context.newRecord(IslandMemberships.ISLAND_MEMBERSHIPS);
        record.setIslandId(getIslandId().getInternal());
        record.setIslandMemberId(getIslandMemberId().getInternal());
        record.setOwner(isOwner());
        return record;
    }

    public IslandMembership toDomain() {
        return new IslandMembership(getIslandMemberId(), getIslandId(), isOwner());
    }

    public static IslandMembershipAnemia toAnemia(IslandMembershipsRecord record) {
        String islandIdRaw = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID);
        String islandMemberIdRaw = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID);
        Boolean isOwner = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.OWNER);
        IslandId islandId = IslandId.of(islandIdRaw);
        IslandMemberId islandMemberId = IslandMemberId.of(islandMemberIdRaw);
        return new IslandMembershipAnemia(islandId, islandMemberId, isOwner);
    }

    public static IslandMembershipAnemia toAnemia(IslandMembership islandMembership) {
        return new IslandMembershipAnemia(islandMembership.getIslandId(), islandMembership.getIslandMemberId(), islandMembership.isOwner());
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

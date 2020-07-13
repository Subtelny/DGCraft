package pl.subtelny.islands.skyblockisland.islandmembership.remover;

import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.skyblockisland.model.MembershipType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IslandMembershipRemoveRequest {

    private final List<IslanderId> islanderIds;

    private final List<IslandId> islandIds;

    private final List<MembershipType> membershipTypes;

    public IslandMembershipRemoveRequest(List<IslanderId> islanderIds, List<IslandId> islandIds, List<MembershipType> membershipTypes) {
        this.islanderIds = islanderIds;
        this.islandIds = islandIds;
        this.membershipTypes = membershipTypes;
    }

    public List<IslanderId> getIslanderIds() {
        return islanderIds;
    }

    public List<IslandId> getIslandIds() {
        return islandIds;
    }

    public List<MembershipType> getMembershipTypes() {
        return membershipTypes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<IslanderId> islanderIds = new ArrayList<>();

        private List<IslandId> islandIds = new ArrayList<>();

        private List<MembershipType> membershipTypes = new ArrayList<>();

        public Builder where(IslanderId... islanderIds) {
            this.islanderIds = Arrays.stream(islanderIds).collect(Collectors.toList());
            return this;
        }

        public Builder where(IslandId... islandIds) {
            this.islandIds = Arrays.stream(islandIds).collect(Collectors.toList());
            return this;
        }

        public Builder where(MembershipType... membershipTypes) {
            this.membershipTypes = Arrays.stream(membershipTypes).collect(Collectors.toList());
            return this;
        }

        public IslandMembershipRemoveRequest build() {
            return new IslandMembershipRemoveRequest(islanderIds, islandIds, membershipTypes);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipRemoveRequest that = (IslandMembershipRemoveRequest) o;
        return Objects.equals(islanderIds, that.islanderIds) &&
                Objects.equals(islandIds, that.islandIds) &&
                Objects.equals(membershipTypes, that.membershipTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islanderIds, islandIds, membershipTypes);
    }
}

package pl.subtelny.islands.islandmembership.remover;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IslandMembershipRemoveRequest {

    private final List<IslandMemberId> islanderIds;

    private final List<IslandId> islandIds;

    private final List<IslandMemberId> notInIslandMemberIds;

    public IslandMembershipRemoveRequest(List<IslandMemberId> islandMemberIds, List<IslandId> islandIds, List<IslandMemberId> notInIslandMemberIds) {
        this.islanderIds = islandMemberIds;
        this.islandIds = islandIds;
        this.notInIslandMemberIds = notInIslandMemberIds;
    }

    public List<IslandMemberId> getNotInIslandMemberIds() {
        return notInIslandMemberIds;
    }

    public List<IslandMemberId> getIslandMemberIds() {
        return islanderIds;
    }

    public List<IslandId> getIslandIds() {
        return islandIds;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<IslandMemberId> islandMemberIds = new ArrayList<>();

        private List<IslandId> islandIds = new ArrayList<>();

        private List<IslandMemberId> notInIslandMemberIds = new ArrayList<>();

        public Builder where(IslandMemberId... islanderIds) {
            this.islandMemberIds = Arrays.stream(islanderIds).collect(Collectors.toList());
            return this;
        }

        public Builder where(IslandId... islandIds) {
            this.islandIds = Arrays.stream(islandIds).collect(Collectors.toList());
            return this;
        }

        public Builder notIn(IslandMemberId... islanderIds) {
            this.notInIslandMemberIds = Arrays.stream(islanderIds).collect(Collectors.toList());
            return this;
        }

        public IslandMembershipRemoveRequest build() {
            return new IslandMembershipRemoveRequest(islandMemberIds, islandIds, notInIslandMemberIds);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMembershipRemoveRequest that = (IslandMembershipRemoveRequest) o;
        return Objects.equals(islanderIds, that.islanderIds) &&
                Objects.equals(islandIds, that.islandIds) &&
                Objects.equals(notInIslandMemberIds, that.notInIslandMemberIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islanderIds, islandIds, notInIslandMemberIds);
    }
}

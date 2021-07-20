package pl.subtelny.islands.islander.repository.loader;

import pl.subtelny.islands.api.IslanderId;

import java.util.*;

public class IslanderLoadRequest {

    private final List<IslanderId> islanderIds;

    public IslanderLoadRequest(List<IslanderId> islanderIds) {
        this.islanderIds = islanderIds;
    }

    public List<IslanderId> getIslanderIds() {
        return islanderIds;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<IslanderId> islanderIds = new ArrayList<>();

        public Builder where(IslanderId islanderId) {
            this.islanderIds = Collections.singletonList(islanderId);
            return this;
        }

        public Builder where(List<IslanderId> islanderIds) {
            this.islanderIds = islanderIds;
            return this;
        }

        public IslanderLoadRequest build() {
            return new IslanderLoadRequest(islanderIds);
        }

    }
}

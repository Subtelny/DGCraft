package pl.subtelny.islands.islander.repository.loader;

import pl.subtelny.islands.island.IslanderId;

import java.util.Optional;

public class IslanderLoadRequest {

    private final IslanderId islanderId;

    public IslanderLoadRequest(IslanderId islanderId) {
        this.islanderId = islanderId;
    }

    public Optional<IslanderId> getIslanderId() {
        return Optional.ofNullable(islanderId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private IslanderId islanderId;

        public Builder where(IslanderId islanderId) {
            this.islanderId = islanderId;
            return this;
        }

        public IslanderLoadRequest build() {
            return new IslanderLoadRequest(islanderId);
        }

    }
}

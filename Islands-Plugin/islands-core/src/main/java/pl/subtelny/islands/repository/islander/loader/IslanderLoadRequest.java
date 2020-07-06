package pl.subtelny.islands.repository.islander.loader;

import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.islander.IslanderId;

import java.util.Optional;

public class IslanderLoadRequest {

    private final IslanderId islanderId;

    private final IslandId skyblockIslandId;

    private final GuildId guildId;

    public IslanderLoadRequest(IslanderId islanderId, IslandId skyblockIslandId, GuildId guildId) {
        this.islanderId = islanderId;
        this.skyblockIslandId = skyblockIslandId;
        this.guildId = guildId;
    }

    public Optional<IslanderId> getIslanderId() {
        return Optional.ofNullable(islanderId);
    }

    public Optional<IslandId> getSkyblockIslandId() {
        return Optional.ofNullable(skyblockIslandId);
    }

    public Optional<GuildId> getGuildId() {
        return Optional.ofNullable(guildId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private IslanderId islanderId;

        private IslandId skyblockIslandId;

        private GuildId guildId;

        public Builder where(IslanderId islanderId) {
            this.islanderId = islanderId;
            return this;
        }

        public Builder where(IslandId skyblockIslandId) {
            this.skyblockIslandId = skyblockIslandId;
            return this;
        }

        public Builder where(GuildId guildId) {
            this.guildId = guildId;
            return this;
        }

        public IslanderLoadRequest build() {
            return new IslanderLoadRequest(islanderId, skyblockIslandId, guildId);
        }

    }
}

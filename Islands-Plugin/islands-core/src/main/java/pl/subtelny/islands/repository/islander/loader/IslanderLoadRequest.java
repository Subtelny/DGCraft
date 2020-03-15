package pl.subtelny.islands.repository.islander.loader;

import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;

import java.util.Optional;

public class IslanderLoadRequest {

    private final AccountId accountId;

    private final IslandId skyblockIslandId;

    private final GuildId guildId;

    public IslanderLoadRequest(AccountId accountId, IslandId skyblockIslandId, GuildId guildId) {
        this.accountId = accountId;
        this.skyblockIslandId = skyblockIslandId;
        this.guildId = guildId;
    }

    public Optional<AccountId> getAccountId() {
        return Optional.ofNullable(accountId);
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

        private AccountId accountId;

        private IslandId skyblockIslandId;

        private GuildId guildId;

        public Builder where(AccountId accountId) {
            this.accountId = accountId;
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
            return new IslanderLoadRequest(accountId, skyblockIslandId, guildId);
        }

    }
}

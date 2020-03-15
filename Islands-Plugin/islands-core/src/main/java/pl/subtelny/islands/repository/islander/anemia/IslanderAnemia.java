package pl.subtelny.islands.repository.islander.anemia;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;

public class IslanderAnemia {

    private AccountId accountId;

    private IslandId skyblockIslandId;

    private GuildId guildId;

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    public IslandId getSkyblockIslandId() {
        return skyblockIslandId;
    }

    public void setSkyblockIslandId(IslandId skyblockIslandId) {
        this.skyblockIslandId = skyblockIslandId;
    }

    public GuildId getGuildId() {
        return guildId;
    }

    public void setGuildId(GuildId guildId) {
        this.guildId = guildId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IslanderAnemia that = (IslanderAnemia) o;

        return new EqualsBuilder()
                .append(accountId, that.accountId)
                .append(skyblockIslandId, that.skyblockIslandId)
                .append(guildId, that.guildId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accountId)
                .append(skyblockIslandId)
                .append(guildId)
                .toHashCode();
    }
}

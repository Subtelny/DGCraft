package pl.subtelny.islands.model.islander;

import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.islands.model.guild.Guild;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.utilities.exception.ValidationException;

public class Islander {

	private final AccountId account;

	private IslandId skyblockIsland;

	private GuildId guild;

	public Islander(AccountId account, IslandId skyblockIsland, GuildId guild) {
		this.account = account;
		this.skyblockIsland = skyblockIsland;
		this.guild = guild;
	}

	public Islander(AccountId account) {
		this.account = account;
	}

	public void setGuild(@Nullable Guild guild) {
		if (guild == null) {
			this.guild = null;
		} else {
			if (!guild.isInGuild(this)) {
				throw new ValidationException("This islander is not added to guild");
			}
			this.guild = guild.getGuildId();
		}
	}

	public void setIsland(@Nullable SkyblockIsland skyblockIsland) {
		if (skyblockIsland == null) {
			this.skyblockIsland = null;
		} else {
			if (!skyblockIsland.isInIsland(this)) {
				throw new ValidationException("This islander is not added to island");
			}
			this.skyblockIsland = skyblockIsland.getIslandId();
		}
	}

	public Optional<GuildId> getGuild() {
		return Optional.ofNullable(guild);
	}

	public Optional<IslandId> getSkyblockIsland() {
		return Optional.ofNullable(skyblockIsland);
	}

	public AccountId getAccount() {
		return account;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Islander islander = (Islander) o;

		return new EqualsBuilder()
				.append(account, islander.account)
				.append(skyblockIsland, islander.skyblockIsland)
				.append(guild, islander.guild)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(account)
				.toHashCode();
	}

}

package pl.subtelny.islands.model;

import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pl.subtelny.core.model.Account;
import pl.subtelny.islands.model.guild.Guild;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.validation.ValidationException;

public class Islander implements IslandMember {

	private final Account account;

	private SkyblockIsland island;

	private Guild guild;

	public Islander(Account account) {
		this.account = account;
	}

	public void setGuild(@Nullable Guild guild) {
		if (guild == null) {
			removeGuild();
		} else {
			if (!guild.isInGuild(this)) {
				throw new ValidationException("This islander is not added to guild");
			}
			this.guild = guild;
		}
	}

	private void removeGuild() {
		if (guild != null) {
			if (guild.isInGuild(this)) {
				throw new ValidationException("Cannot remove Islander's guild. Firstly remove Islander from guild");
			}
			guild = null;
		}
	}

	public void setIsland(@Nullable SkyblockIsland island) {
		if (island == null) {
			removeIsland();
		} else {
			if (!island.isInIsland(this)) {
				throw new ValidationException("This islander is not added to island");
			}
			this.island = island;
		}
	}

	private void removeIsland() {
		if (island != null) {
			if (island.isInIsland(this)) {
				throw new ValidationException("Cannot remove Islander's island. Firstly remove Islander from island");
			}
			island = null;
		}
	}

	public Optional<Guild> getGuild() {
		return Optional.ofNullable(guild);
	}

	public Account getAccount() {
		return account;
	}

	public Optional<SkyblockIsland> getIsland() {
		return Optional.ofNullable(island);
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
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(account)
				.toHashCode();
	}

	@Override
	public IslandMemberType getIslandMemberType() {
		return IslandMemberType.ISLANDER;
	}
}

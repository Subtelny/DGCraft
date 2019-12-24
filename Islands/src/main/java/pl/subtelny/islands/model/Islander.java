package pl.subtelny.islands.model;

import java.util.Optional;
import javax.annotation.Nullable;
import pl.subtelny.core.model.Account;
import pl.subtelny.validation.ValidationException;

public class Islander implements IslandMember {

	private final Account account;

	private Island island;

	public Islander(Account account) {
		this.account = account;
	}

	public Optional<Island> getIsland() {
		return Optional.ofNullable(island);
	}

	public Account getAccount() {
		return account;
	}

	public void setIsland(@Nullable Island island) {
		if (island == null) {
			this.island = null;
			return;
		}
		if (island.getMembers().contains(this)) {
			this.island = island;
		} else {
			throw new ValidationException(String.format("This islander is not added to island %s", island.getIslandId()));
		}
	}
}

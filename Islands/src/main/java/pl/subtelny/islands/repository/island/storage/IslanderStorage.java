package pl.subtelny.islands.repository.island.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.api.Accounts;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.repository.Storage;

@Component
public class IslanderStorage extends Storage<AccountId, Optional<Islander>> {

	private final Accounts accounts;

	@Autowired
	public IslanderStorage(Accounts accounts) {
		super(Caffeine.newBuilder().build());
		this.accounts = accounts;
	}

	public Optional<Islander> findIslander(Player player) {
		AccountId accountId = AccountId.of(player.getUniqueId());
		return getCache(accountId, null);
	}

	private Optional<Islander> loadIslander(AccountId accountId) {
		Optional<Account> account = accounts.findAccount(accountId);
		account.orElseThrow(() -> new NoSuchElementException(String.format("Not found Account for id %s", accountId)));
		Islander islander = new Islander(account.get());
		return Optional.of(islander);
	}
}

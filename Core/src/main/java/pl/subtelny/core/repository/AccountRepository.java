package pl.subtelny.core.repository;

import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.loader.AccountLoadRequest;
import pl.subtelny.core.repository.loader.AccountLoader;
import pl.subtelny.core.repository.storage.AccountStorage;
import pl.subtelny.core.repository.updater.AccountUpdater;
import pl.subtelny.database.DatabaseConfiguration;

@Component
public class AccountRepository {

	private final AccountStorage accountStorage;

	private final AccountUpdater accountUpdater;

	private final AccountLoader accountLoader;

	@Autowired
	public AccountRepository(DatabaseConfiguration databaseConfiguration) {
		accountStorage = new AccountStorage();
		Configuration configuration = databaseConfiguration.getConfiguration();
		accountUpdater = new AccountUpdater(configuration);
		accountLoader = new AccountLoader(configuration);
	}

	public Optional<Account> getAccountIfPresent(AccountId accountId) {
		return accountStorage.getCacheIfPresent(accountId);
	}

	public Optional<Account> findAccount(AccountId accountId) {
		return accountStorage.getCache(accountId, accountId1 -> {
			AccountLoadRequest request = AccountLoadRequest.newBuilder()
					.where(accountId1)
					.build();
			return accountLoader.loadAccount(request);
		});
	}

	public void saveAccount(Account account) {
		accountStorage.put(account.getAccountId(), Optional.of(account));
		accountUpdater.updateAccount(account.getAccountAnemia());
	}

}

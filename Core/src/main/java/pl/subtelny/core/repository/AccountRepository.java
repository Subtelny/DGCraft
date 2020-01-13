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

@Component
public class AccountRepository {

	private final AccountStorage accountStorage;

	private final AccountUpdater accountUpdater;

	private final AccountLoader accountLoader;

	@Autowired
	public AccountRepository(Configuration configuration) {
		accountStorage = new AccountStorage();
		accountUpdater = new AccountUpdater(configuration);
		accountLoader = new AccountLoader(configuration);
	}

	public Optional<Account> findAccount(AccountId accountId) {
		return accountStorage.getCache(accountId, accountId1 -> {
			AccountLoadRequest request = AccountLoadRequest.newBuilder()
					.where(accountId1)
					.build();
			return accountLoader.loadAccount(request);
		});
	}

	private Optional<Account> findAccount(AccountLoadRequest request) {
		Optional<AccountId> accountId = accountStorage.getCache(request, request1 -> {
			Optional<Account> accountOpt = accountLoader.loadAccount(request1);
			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				AccountId id = account.getAccountId();
				accountStorage.putIfAbsent(id, accountOpt);
				return Optional.of(id);
			}
			return Optional.empty();
		});
		if (accountId.isPresent()) {
			return findAccount(accountId.get());
		}
		return Optional.empty();
	}

	public void saveAccount(AccountAnemia accountAnemia) {
		accountStorage.invalidateRequests(accountAnemia);
		accountUpdater.updateAccount(accountAnemia);
	}

}

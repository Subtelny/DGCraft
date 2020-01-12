package pl.subtelny.core.repository;

import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.loader.AccountAnemia;
import pl.subtelny.core.repository.loader.AccountAnemiaLoadAction;
import pl.subtelny.core.repository.loader.AccountAnemiaLoadRequest;

import java.util.List;
import java.util.Optional;
import pl.subtelny.core.repository.storage.AccountStorage;

@Component
public class AccountRepository {

	private final AccountStorage accountStorage;

	private final Configuration configuration;

	@Autowired
	public AccountRepository(Configuration configuration) {
		this.configuration = configuration;
		accountStorage = new AccountStorage();
	}

	public Optional<Account> findAccount(AccountId accountId) {
		return accountStorage.getCache(accountId, accountId1 -> {
			AccountAnemiaLoadRequest request = AccountAnemiaLoadRequest.newBuilder()
					.where(accountId1)
					.build();
			return findAccount(request);
		});
	}

	private Optional<Account> findAccount(AccountAnemiaLoadRequest request) {
		Optional<AccountId> accountId = accountStorage.getCache(request, request1 -> {
			Optional<Account> accountOpt = loadAccount(request1);
			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				AccountId id = account.getAccountId();
				accountStorage.putIfAbsent(id, accountOpt);
				return Optional.of(id);
			}
			return Optional.empty();
		});
		if (accountId.isPresent()) {
			return accountStorage.getCacheIfPresent(accountId.get());
		}
		return Optional.empty();
	}

	private Optional<Account> loadAccount(AccountAnemiaLoadRequest request) {
		Optional<AccountAnemia> account = performAction(request);
		if (account.isPresent()) {
			return Optional.of(AccountMapper.map(account.get()));
		}
		return Optional.empty();
	}

	private Optional<AccountAnemia> performAction(AccountAnemiaLoadRequest request) {
		AccountAnemiaLoadAction loader = new AccountAnemiaLoadAction(configuration, request);
		List<AccountAnemia> loadedData = loader.perform().getLoadedData();
		if (loadedData.size() == 0) {
			return Optional.empty();
		}
		return Optional.of(loadedData.get(0));
	}

	public void saveAccount(AccountAnemia account) {
		//TODO
		//to implement
	}

	private static class AccountMapper {

		private static Account map(AccountAnemia accountAnemia) {
			return new Account(accountAnemia);
		}

	}

}

package pl.subtelny.core.repository.loader;

import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.repository.AccountAnemia;

public class AccountLoader {

	private final Configuration configuration;

	public AccountLoader(Configuration configuration) {
		this.configuration = configuration;
	}

	public Optional<Account> loadAccount(AccountLoadRequest request) {
		Optional<AccountAnemia> accountAnemia = performAction(request);
		if (accountAnemia.isPresent()) {
			Account account = AccountMapper.map(accountAnemia.get());
			return Optional.of(account);
		}
		return Optional.empty();
	}

	private Optional<AccountAnemia> performAction(AccountLoadRequest request) {
		AccountAnemiaLoadAction loader = new AccountAnemiaLoadAction(configuration, request);
		AccountAnemia loadedData = loader.perform().getLoadedData();
		return Optional.ofNullable(loadedData);
	}

	private static class AccountMapper {

		private static Account map(AccountAnemia accountAnemia) {
			return new Account(accountAnemia);
		}

	}

}

package pl.subtelny.core.repository;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.model.LoginHistory;

@Component
public class AccountRepository {

	public Account getAccount(AccountId accountId) {
		//TODO
		//to implement
		return new Account(accountId);
	}

	public Optional<Account> findAccount(AccountId accountId) {
		//TODO
		//to implement
		return Optional.empty();
	}

	public void saveAccount(Account account) {
		//TODO
		//to implement
	}

	public void saveLoginHistory(LoginHistory loginHistory) {
		//TODO
		//to implement
	}

	public Set<LoginHistory> getLoginHistoriesOfAccount(AccountId accountId) {
		//TODO
		//to implement
		return Sets.newHashSet();
	}

}

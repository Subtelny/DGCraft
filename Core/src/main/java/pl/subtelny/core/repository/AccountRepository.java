package pl.subtelny.core.repository;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Component;
import pl.subtelny.core.api.Accounts;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.model.LoginHistory;

@Component
public class AccountRepository implements Accounts {

	@Override
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

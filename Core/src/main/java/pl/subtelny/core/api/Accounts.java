package pl.subtelny.core.api;

import java.util.Optional;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;

public interface Accounts {

	Account getAccount(AccountId accountId);

	Optional<Account> findAccount(AccountId accountId);

}

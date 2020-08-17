package pl.subtelny.core.repository.account;

import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CreateAccountRequest;

import javax.annotation.Nullable;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> getAccountIfPresent(AccountId accountId);

    Optional<Account> findAccount(AccountId accountId);

    Account createAccount(CreateAccountRequest request);

    void saveAccount(Account account);
}

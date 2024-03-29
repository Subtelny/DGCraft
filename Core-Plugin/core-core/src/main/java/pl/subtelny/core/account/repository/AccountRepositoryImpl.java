package pl.subtelny.core.account.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CreateAccountRequest;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.account.repository.model.CoreAccount;
import pl.subtelny.core.account.repository.loader.AccountLoadRequest;
import pl.subtelny.core.account.repository.loader.AccountLoader;
import pl.subtelny.core.account.repository.storage.AccountStorage;
import pl.subtelny.core.account.repository.updater.AccountUpdater;
import pl.subtelny.utilities.NullObject;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountStorage accountStorage;

    private final AccountUpdater accountUpdater;

    private final AccountLoader accountLoader;

    @Autowired
    public AccountRepositoryImpl(ConnectionProvider connectionProvider) {
        accountStorage = new AccountStorage();
        accountUpdater = new AccountUpdater(connectionProvider);
        accountLoader = new AccountLoader(connectionProvider);
    }

    @Override
    public Optional<Account> getAccountIfPresent(AccountId accountId) {
        return accountStorage.getCacheIfPresent(accountId).flatMap(NullObject::get);
    }

    @Override
    public Optional<Account> findAccount(AccountId accountId) {
        return accountStorage.getCache(accountId, accountIdToFind -> {
            AccountLoadRequest request = AccountLoadRequest.newBuilder()
                    .where(accountIdToFind)
                    .build();
            Optional<Account> accountOpt = accountLoader.loadAccount(request);
            return accountOpt.map(NullObject::of).orElse(NullObject.empty());
        }).get();
    }

    @Override
    public Account createAccount(CreateAccountRequest request) {
        CoreAccount account = new CoreAccount(
                request.getAccountId(),
                request.getName(),
                request.getDisplayName(),
                LocalDateTime.now()
        );
        saveAccount(account);
        return account;
    }

    @Override
    public void saveAccount(Account account) {
        accountStorage.put(account.getAccountId(), NullObject.of(account));
        accountUpdater.updateAccount(toAnemia(account));
    }

    private AccountAnemia toAnemia(Account entity) {
        AccountAnemia anemia = new AccountAnemia(entity.getAccountId());
        anemia.setDisplayName(entity.getDisplayName());
        anemia.setLastOnline(entity.getLastOnline());
        anemia.setName(entity.getName());
        return anemia;
    }

}

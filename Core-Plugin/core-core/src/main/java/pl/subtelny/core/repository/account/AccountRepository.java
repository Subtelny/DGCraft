package pl.subtelny.core.repository.account;

import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CreateAccountRequest;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.repository.account.entity.AccountEntity;
import pl.subtelny.core.repository.account.loader.AccountLoadRequest;
import pl.subtelny.core.repository.account.loader.AccountLoader;
import pl.subtelny.core.repository.account.storage.AccountStorage;
import pl.subtelny.core.repository.account.updater.AccountUpdater;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AccountRepository {

    private final AccountStorage accountStorage;

    private final AccountUpdater accountUpdater;

    private final AccountLoader accountLoader;

    @Autowired
    public AccountRepository(DatabaseConnection databaseConfiguration) {
        accountStorage = new AccountStorage();
        Configuration configuration = databaseConfiguration.getConfiguration();
        accountUpdater = new AccountUpdater(configuration);
        accountLoader = new AccountLoader(configuration);
    }

    public Optional<Account> getAccountIfPresent(AccountId accountId) {
        return accountStorage.getCacheIfPresent(accountId);
    }

    public Optional<Account> findAccount(AccountId accountId) {
        return accountStorage.getCache(accountId, accountIdToFind -> {
            AccountLoadRequest request = AccountLoadRequest.newBuilder()
                    .where(accountIdToFind)
                    .build();
            return accountLoader.loadAccount(request);
        });
    }

    public void createAccount(CreateAccountRequest request) {
        AccountEntity account = new AccountEntity(
                request.getAccountId(),
                request.getName(),
                request.getDisplayName(),
                LocalDateTime.now(),
                request.getCityType()
        );
        saveAccount(account);
    }

    public void saveAccount(Account account) {
        accountStorage.put(account.getAccountId(), Optional.of(account));
        accountUpdater.updateAccountAsync(toAnemia(account));
    }

    private AccountAnemia toAnemia(Account entity) {
        AccountAnemia anemia = new AccountAnemia(entity.getAccountId());
        anemia.setDisplayName(entity.getDisplayName());
        anemia.setLastOnline(entity.getLastOnline());
        anemia.setName(entity.getName());
        return anemia;
    }

}

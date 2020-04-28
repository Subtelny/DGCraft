package pl.subtelny.core.repository.account;

import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.api.database.DatabaseConfiguration;
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
        return accountStorage.getCache(accountId, accountIdToFind -> {
            AccountLoadRequest request = AccountLoadRequest.newBuilder()
                    .where(accountIdToFind)
                    .build();
            return accountLoader.loadAccount(request);
        });
    }

    public void createAccount(AccountId accountId, String name, String displayName, CityType cityType) {
        AccountEntity account = new AccountEntity(
                accountId,
                name,
                displayName,
                LocalDateTime.now(),
                cityType
        );
        saveAccount(account);
    }

    public void saveAccount(AccountEntity account) {
        accountStorage.put(account.getAccountId(), Optional.of(account));
        accountUpdater.updateAccountAsync(toAnemia(account));
    }

    private AccountAnemia toAnemia(AccountEntity entity) {
        AccountAnemia anemia = new AccountAnemia(entity.getAccountId());
        anemia.setDisplayName(entity.getDisplayName());
        anemia.setLastOnline(entity.getLastOnline());
        anemia.setName(entity.getName());
        return anemia;
    }

}

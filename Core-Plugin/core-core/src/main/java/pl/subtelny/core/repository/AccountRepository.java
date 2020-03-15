package pl.subtelny.core.repository;

import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.DatabaseConfiguration;
import pl.subtelny.core.repository.entity.AccountEntity;
import pl.subtelny.core.repository.loader.AccountLoadRequest;
import pl.subtelny.core.repository.loader.AccountLoader;
import pl.subtelny.core.repository.storage.AccountStorage;
import pl.subtelny.core.repository.updater.AccountUpdater;

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

    public Optional<AccountEntity> getAccountIfPresent(AccountId accountId) {
        return accountStorage.getCacheIfPresent(accountId);
    }

    public Optional<AccountEntity> findAccount(AccountId accountId) {
        return accountStorage.getCache(accountId, accountIdToFind -> {
            AccountLoadRequest request = AccountLoadRequest.newBuilder()
                    .where(accountIdToFind)
                    .build();
            return accountLoader.loadAccount(request);
        });
    }

    public void saveAccount(AccountEntity account) {
        accountStorage.put(account.getAccountId(), Optional.of(account));
        accountUpdater.updateAccount(toAnemia(account));
    }

    private AccountAnemia toAnemia(AccountEntity entity) {
        AccountAnemia anemia = new AccountAnemia(entity.getAccountId());
        anemia.setDisplayName(entity.getDisplayName());
        anemia.setLastOnline(entity.getLastOnline());
        anemia.setName(entity.getName());
        return anemia;
    }

}

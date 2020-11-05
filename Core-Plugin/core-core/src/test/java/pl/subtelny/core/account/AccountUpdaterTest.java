package pl.subtelny.core.account;

import org.junit.Assert;
import org.junit.Test;
import pl.subtelny.core.account.repository.AccountAnemia;
import pl.subtelny.core.account.repository.loader.AccountLoadRequest;
import pl.subtelny.core.account.repository.loader.AccountLoader;
import pl.subtelny.core.account.repository.updater.AccountUpdater;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.database.ConnectionConfigurationProviderImpl;
import pl.subtelny.core.database.TransactionProviderImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AccountUpdaterTest {

    private final AccountUpdater updater;

    private final AccountLoader loader;

    public AccountUpdaterTest() {
        DatabaseConnection databaseConnection = new DatabaseConnectionImplTest();
        TransactionProvider transactionProvider = new TransactionProviderImpl(databaseConnection);
        ConnectionProvider provider = new ConnectionConfigurationProviderImpl(databaseConnection, transactionProvider);
        this.updater = new AccountUpdater(provider);
        this.loader = new AccountLoader(provider);
    }

    @Test
    public void createAccount() {
        //given
        String name = "test_name";
        AccountId accountId = AccountId.of(UUID.randomUUID());
        AccountAnemia anemia = createAnemia(accountId, name);

        //when
        updateAccount(anemia);

        //then
        Optional<Account> account = loadAccount(accountId);

        Assert.assertTrue(account.isPresent());
        Assert.assertEquals(account.get().getName(), name);
    }

    @Test
    public void updateAccount() {
        //given
        String name = "test_name";
        String newName = "new_name";
        AccountId accountId = AccountId.of(UUID.randomUUID());
        AccountAnemia anemia = createAnemia(accountId, name);
        AccountAnemia newAnemia = createAnemia(accountId, newName);
        updateAccount(anemia);

        //when
        Optional<Account> account = loadAccount(accountId);

        //then
        updateAccount(newAnemia);
        Optional<Account> updatedAccount = loadAccount(accountId);

        Assert.assertTrue(account.isPresent());
        Assert.assertEquals(account.get().getAccountId(), accountId);
        Assert.assertEquals(account.get().getName(), name);
        Assert.assertTrue(updatedAccount.isPresent());
        Assert.assertEquals(updatedAccount.get().getAccountId(), accountId);
        Assert.assertEquals(updatedAccount.get().getName(), newName);
    }

    private void updateAccount(AccountAnemia anemia) {
        updater.updateAccount(anemia);
    }

    private Optional<Account> loadAccount(AccountId accountId) {
        AccountLoadRequest request = AccountLoadRequest.newBuilder().where(accountId).build();
        return loader.loadAccount(request);
    }

    private AccountAnemia createAnemia(AccountId accountId, String name) {
        return new AccountAnemia(accountId, name, name, LocalDateTime.now(), null);
    }

}

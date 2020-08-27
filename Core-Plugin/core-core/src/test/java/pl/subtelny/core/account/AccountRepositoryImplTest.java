package pl.subtelny.core.account;

import pl.subtelny.core.account.repository.AccountRepository;
import pl.subtelny.core.account.repository.AccountRepositoryImpl;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CreateAccountRequest;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.database.TransactionProviderImpl;
import pl.subtelny.core.account.repository.upgrade.v1.AccountDBUpgradeV1;

import javax.annotation.Nullable;
import java.util.Optional;

public class AccountRepositoryImplTest implements AccountRepository {

    private final AccountRepository accountRepository;

    private final DatabaseConnection databaseConnection;

    public AccountRepositoryImplTest() {
       this.databaseConnection = new DatabaseConnectionImplTest();
        TransactionProvider transactionProvider = new TransactionProviderImpl(databaseConnection);
        this.accountRepository = new AccountRepositoryImpl(databaseConnection, transactionProvider);

        initializeTables();
    }

    private void initializeTables() {
        new AccountDBUpgradeV1(databaseConnection).execute();
    }

    @Nullable
    @Override
    public Optional<Account> getAccountIfPresent(AccountId accountId) {
        return accountRepository.getAccountIfPresent(accountId);
    }

    @Override
    public Optional<Account> findAccount(AccountId accountId) {
        return accountRepository.findAccount(accountId);
    }

    @Override
    public Account createAccount(CreateAccountRequest request) {
        return accountRepository.createAccount(request);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.saveAccount(account);
    }
}

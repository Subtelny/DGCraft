package pl.subtelny.core.account.repository.loader;

import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.account.repository.AccountAnemia;
import pl.subtelny.core.account.repository.entity.AccountEntity;

import java.util.Optional;

public class AccountLoader {

    private final DatabaseConnection databaseConnection;

    public AccountLoader(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Optional<Account> loadAccount(AccountLoadRequest request) {
        Optional<AccountAnemia> accountAnemia = performAction(request);
        return accountAnemia.map(AccountMapper::map);
    }

    private Optional<AccountAnemia> performAction(AccountLoadRequest request) {
        AccountAnemiaLoadAction loader = new AccountAnemiaLoadAction(databaseConnection.getConfiguration(), request);
        AccountAnemia loadedData = loader.perform();
        return Optional.ofNullable(loadedData);
    }

    private static class AccountMapper {

        private static AccountEntity map(AccountAnemia accountAnemia) {
			return new AccountEntity(
					accountAnemia.getAccountId(),
					accountAnemia.getName(),
					accountAnemia.getDisplayName(),
					accountAnemia.getLastOnline(),
                    accountAnemia.getCityId()
			);
        }

    }

}

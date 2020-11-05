package pl.subtelny.core.account.repository.loader;

import org.jooq.DSLContext;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.account.repository.AccountAnemia;
import pl.subtelny.core.account.repository.model.CoreAccount;

import java.util.Optional;

public class AccountLoader {

    private final ConnectionProvider connectionProvider;

    public AccountLoader(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Optional<Account> loadAccount(AccountLoadRequest request) {
        Optional<AccountAnemia> accountAnemia = performAction(request);
        return accountAnemia.map(AccountMapper::map);
    }

    private Optional<AccountAnemia> performAction(AccountLoadRequest request) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        AccountAnemiaLoadAction loader = new AccountAnemiaLoadAction(connection, request);
        AccountAnemia loadedData = loader.perform();
        return Optional.ofNullable(loadedData);
    }

    private static class AccountMapper {

        private static CoreAccount map(AccountAnemia accountAnemia) {
			return new CoreAccount(
					accountAnemia.getAccountId(),
					accountAnemia.getName(),
					accountAnemia.getDisplayName(),
					accountAnemia.getLastOnline(),
                    accountAnemia.getCityId()
			);
        }

    }

}

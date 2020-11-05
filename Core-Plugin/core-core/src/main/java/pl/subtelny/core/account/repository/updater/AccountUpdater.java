package pl.subtelny.core.account.repository.updater;

import org.jooq.DSLContext;
import pl.subtelny.core.account.repository.AccountAnemia;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.repository.Updater;

public class AccountUpdater implements Updater<AccountAnemia, AccountId> {

    private final ConnectionProvider connectionProvider;

    public AccountUpdater(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public AccountId updateAccount(AccountAnemia accountAnemia) {
        return performAction(accountAnemia);
    }

    @Override
    public AccountId performAction(AccountAnemia entity) {
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(currentConnection);
        return action.perform(entity);
    }

}

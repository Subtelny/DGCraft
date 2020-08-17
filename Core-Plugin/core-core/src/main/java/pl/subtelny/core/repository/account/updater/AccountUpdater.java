package pl.subtelny.core.repository.account.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.repository.account.AccountAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class AccountUpdater extends Updater<AccountAnemia, AccountId> {

	public AccountUpdater(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        super(databaseConnection, transactionProvider);
	}

	public void updateAccount(AccountAnemia accountAnemia) {
		performAction(accountAnemia);
	}

	public CompletableFuture<AccountId> updateAccountAsync(AccountAnemia accountAnemia) {
		return performActionAsync(accountAnemia);
	}

	@Override
	protected AccountId performAction(AccountAnemia entity) {
		Configuration configuration = getConfiguration();
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(configuration);
		return action.perform(entity);
	}

	@Override
	protected CompletableFuture<AccountId> performActionAsync(AccountAnemia accountAnemia) {
		Configuration configuration = getConfiguration();
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(configuration);
		return action.performAsync(accountAnemia).toCompletableFuture();
	}

}

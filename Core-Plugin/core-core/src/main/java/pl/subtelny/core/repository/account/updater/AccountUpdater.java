package pl.subtelny.core.repository.account.updater;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.repository.account.AccountAnemia;
import org.jooq.Configuration;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class AccountUpdater extends Updater<AccountAnemia> {

	private final DatabaseConnection databaseConnection;

	public AccountUpdater(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public void updateAccount(AccountAnemia accountAnemia) {
		performAction(accountAnemia);
	}

	public void updateAccountAsync(AccountAnemia accountAnemia) {
		performActionAsync(accountAnemia);
	}

	@Override
	protected void performAction(AccountAnemia entity) {
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(databaseConnection.getConfiguration());
		action.perform(entity);
	}

	@Override
	protected CompletableFuture<Integer> performActionAsync(AccountAnemia accountAnemia) {
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(databaseConnection.getConfiguration());
		return action.performAsync(accountAnemia).toCompletableFuture();
	}

}

package pl.subtelny.core.repository.account.updater;

import pl.subtelny.core.repository.account.AccountAnemia;
import org.jooq.Configuration;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class AccountUpdater extends Updater<AccountAnemia> {

	private final Configuration configuration;

	public AccountUpdater(Configuration configuration) {
		this.configuration = configuration;
	}

	public void updateAccount(AccountAnemia accountAnemia) {
		performAction(accountAnemia);
	}

	public void updateAccountAsync(AccountAnemia accountAnemia) {
		performActionAsync(accountAnemia);
	}

	@Override
	protected void performAction(AccountAnemia entity) {
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(configuration);
		action.perform(entity);
	}

	@Override
	protected CompletableFuture<Integer> performActionAsync(AccountAnemia accountAnemia) {
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(configuration);
		return action.performAsync(accountAnemia).toCompletableFuture();
	}

}

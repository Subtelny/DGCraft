package pl.subtelny.core.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.repository.AccountAnemia;
import pl.subtelny.repository.Updater;

public class AccountUpdater extends Updater<AccountAnemia> {

	private final Configuration configuration;

	public AccountUpdater(Configuration configuration) {
		this.configuration = configuration;
	}

	public void updateAccount(AccountAnemia accountAnemia) {
		addToQueue(accountAnemia);
	}

	@Override
	protected void performAction(AccountAnemia accountAnemia) {
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(configuration);
		action.perform(accountAnemia);
	}

}
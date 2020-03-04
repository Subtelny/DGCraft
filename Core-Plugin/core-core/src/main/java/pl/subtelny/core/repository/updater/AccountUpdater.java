package pl.subtelny.core.repository.updater;

import pl.subtelny.core.repository.AccountAnemia;
import org.jooq.Configuration;
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
	protected void performAction(AccountAnemia entity) {
		AccountAnemiaUpdateAction action = new AccountAnemiaUpdateAction(configuration);
		action.perform(entity);
	}

}

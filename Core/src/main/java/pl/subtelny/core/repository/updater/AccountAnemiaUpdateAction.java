package pl.subtelny.core.repository.updater;

import java.sql.Timestamp;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.Accounts;
import pl.subtelny.core.generated.tables.records.AccountsRecord;
import pl.subtelny.core.repository.AccountAnemia;
import pl.subtelny.repository.UpdateAction;

public class AccountAnemiaUpdateAction implements UpdateAction<AccountAnemia> {

	private final Configuration configuration;

	public AccountAnemiaUpdateAction(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void perform(AccountAnemia accountAnemia) {
		saveAccountAnemia(accountAnemia);
	}

	private void saveAccountAnemia(AccountAnemia accountAnemia) {
		AccountsRecord record = DSL.using(configuration).newRecord(Accounts.ACCOUNTS);

		Timestamp lastOnline = Timestamp.valueOf(accountAnemia.getLastOnline());
		record.setLastOnline(lastOnline);
		record.setName(accountAnemia.getName());
		record.setDisplayName(accountAnemia.getDisplayName());

		if (accountAnemia.getAccountId().getId() != null) {
			record.setId(accountAnemia.getAccountId().getId());
			record.update();
		} else {
			record.store();
		}
	}
}

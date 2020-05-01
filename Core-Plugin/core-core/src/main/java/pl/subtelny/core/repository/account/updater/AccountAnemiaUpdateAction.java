package pl.subtelny.core.repository.account.updater;

import java.sql.Timestamp;
import java.util.concurrent.CompletionStage;

import org.jooq.InsertOnDuplicateSetMoreStep;
import pl.subtelny.core.repository.account.AccountAnemia;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.enums.Citytype;
import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.records.AccountsRecord;
import pl.subtelny.repository.UpdateAction;

public class AccountAnemiaUpdateAction implements UpdateAction<AccountAnemia> {

	private final Configuration configuration;

	public AccountAnemiaUpdateAction(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void perform(AccountAnemia accountAnemia) {
		prepareExecute(accountAnemia).execute();
	}

	@Override
	public CompletionStage<Integer> performAsync(AccountAnemia accountAnemia) {
		return prepareExecute(accountAnemia).executeAsync();
	}

	private InsertOnDuplicateSetMoreStep<AccountsRecord> prepareExecute(AccountAnemia accountAnemia) {
		AccountsRecord record = toRecord(accountAnemia);
		return DSL.using(configuration)
				.insertInto(Accounts.ACCOUNTS)
				.set(record)
				.onDuplicateKeyUpdate()
				.set(record);
	}

	private AccountsRecord toRecord(AccountAnemia accountAnemia) {
		AccountsRecord record = DSL.using(configuration).newRecord(Accounts.ACCOUNTS);
		record.setId(accountAnemia.getAccountId().getId());
		record.setName(accountAnemia.getName());
		record.setDisplayName(accountAnemia.getDisplayName());
		record.setLastOnline(Timestamp.valueOf(accountAnemia.getLastOnline()));
		record.setCity(Citytype.RED);
		return record;
	}
}

package pl.subtelny.core.account.repository.updater;

import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import pl.subtelny.core.account.repository.AccountAnemia;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.api.repository.UpdateAction;
import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.records.AccountsRecord;

import java.sql.Timestamp;

public class AccountAnemiaUpdateAction implements UpdateAction<AccountAnemia, AccountId> {

    private final DSLContext connection;

    public AccountAnemiaUpdateAction(DSLContext connection) {
        this.connection = connection;
    }

    @Override
    public AccountId perform(AccountAnemia accountAnemia) {
        AccountId accountId = accountAnemia.getAccountId();
        prepareExecute(accountAnemia).execute();
        return accountId;
    }

    private InsertOnDuplicateSetMoreStep<AccountsRecord> prepareExecute(AccountAnemia accountAnemia) {
        AccountsRecord record = toRecord(accountAnemia);
        return connection.insertInto(Accounts.ACCOUNTS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private AccountsRecord toRecord(AccountAnemia accountAnemia) {
        AccountsRecord record = connection.newRecord(Accounts.ACCOUNTS);
        record.setId(accountAnemia.getAccountId().getInternal());
        record.setName(accountAnemia.getName());
        record.setDisplayName(accountAnemia.getDisplayName());
        record.setLastOnline(Timestamp.valueOf(accountAnemia.getLastOnline()));
        CityId cityId = accountAnemia.getCityId();
        if (cityId != null) {
            record.setCity(cityId.getInternal());
        }
        return record;
    }
}

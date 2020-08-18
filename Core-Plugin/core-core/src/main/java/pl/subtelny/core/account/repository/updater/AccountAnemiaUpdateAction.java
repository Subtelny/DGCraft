package pl.subtelny.core.account.repository.updater;

import org.jooq.Configuration;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.impl.DSL;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.account.repository.AccountAnemia;
import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.records.AccountsRecord;
import pl.subtelny.repository.UpdateAction;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

public class AccountAnemiaUpdateAction implements UpdateAction<AccountAnemia, AccountId> {

    private final Configuration configuration;

    public AccountAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public AccountId perform(AccountAnemia accountAnemia) {
        AccountId accountId = accountAnemia.getAccountId();
        prepareExecute(accountAnemia).execute();
        return accountId;
    }

    @Override
    public CompletableFuture<AccountId> performAsync(AccountAnemia accountAnemia) {
        return prepareExecute(accountAnemia).executeAsync()
                .toCompletableFuture()
                .thenApply(integer -> accountAnemia.getAccountId());
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

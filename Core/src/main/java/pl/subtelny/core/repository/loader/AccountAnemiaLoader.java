package pl.subtelny.core.repository.loader;

import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.Accounts;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.repository.Loader;
import pl.subtelny.repository.LoaderResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AccountAnemiaLoader extends Loader<AccountAnemia> {

    private final Configuration configuration;

    private final AccountAnemiaLoaderRequest request;

    public AccountAnemiaLoader(Configuration configuration, AccountAnemiaLoaderRequest request) {
        this.configuration = configuration;
        this.request = request;
    }

    @Override
    public LoaderResult<AccountAnemia> perform() {
        AccountAnemia accountAnemias = loadAccountAnemia();
        return new LoaderResult<>(accountAnemias);
    }

    private AccountAnemia loadAccountAnemia() {
        return DSL.using(configuration)
                .select()
                .from(Accounts.ACCOUNTS)
                .where(request.getWhere())
                .fetchOne(this::mapToAccountAnemia);
    }

    private AccountAnemia mapToAccountAnemia(Record record) {
        UUID uuid = record.get(Accounts.ACCOUNTS.ID);
        String name = record.get(Accounts.ACCOUNTS.NAME);
        String displayName = record.get(Accounts.ACCOUNTS.DISPLAY_NAME);
        Timestamp lastOnlineRaw = record.get(Accounts.ACCOUNTS.LAST_ONLINE);

        AccountId accountId = AccountId.of(uuid);
        LocalDateTime lastOnline = lastOnlineRaw.toLocalDateTime();
        return new AccountAnemia(accountId, name, displayName, lastOnline);
    }

}

package pl.subtelny.core.repository.account.loader;

import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.generated.tables.enums.Citytype;
import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.core.repository.account.AccountAnemia;
import pl.subtelny.repository.LoadAction;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AccountAnemiaLoadAction implements LoadAction<AccountAnemia> {

    private final Configuration configuration;

    private final AccountAnemiaLoaderRequest request;

    public AccountAnemiaLoadAction(Configuration configuration, AccountLoadRequest request) {
        this.configuration = configuration;
        this.request = AccountAnemiaLoaderRequest.toRequest(request);
    }

    @Override
    public AccountAnemia perform() {
        return loadOneAccountAnemia();
    }

    @Override
    public List<AccountAnemia> performList() {
        return loadAllAccountAnemia();
    }

    private AccountAnemia loadOneAccountAnemia() {
        return constructQuery()
                .fetchOne(this::mapToAccountAnemia);
    }

    private List<AccountAnemia> loadAllAccountAnemia() {
        return constructQuery()
                .fetch(this::mapToAccountAnemia);
    }

    private SelectConditionStep<Record> constructQuery() {
        return DSL.using(configuration)
                .select()
                .from(Accounts.ACCOUNTS)
                .where(request.getWhere());
    }

    private AccountAnemia mapToAccountAnemia(Record record) {
        UUID uuid = record.get(Accounts.ACCOUNTS.ID);
        String name = record.get(Accounts.ACCOUNTS.NAME);
        String displayName = record.get(Accounts.ACCOUNTS.DISPLAY_NAME);
        Citytype city = record.get(Accounts.ACCOUNTS.CITY);
        Timestamp lastOnlineRaw = record.get(Accounts.ACCOUNTS.LAST_ONLINE);

        AccountId accountId = AccountId.of(uuid);
        LocalDateTime lastOnline = lastOnlineRaw.toLocalDateTime();
        CityType cityType = CityType.of(city.getLiteral());
        return new AccountAnemia(accountId, name, displayName, lastOnline, cityType);
    }

}

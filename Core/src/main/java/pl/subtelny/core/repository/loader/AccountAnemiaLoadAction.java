package pl.subtelny.core.repository.loader;

import com.google.common.collect.Lists;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.Accounts;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountAnemia;
import pl.subtelny.repository.LoadAction;
import pl.subtelny.repository.LoaderResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AccountAnemiaLoadAction implements LoadAction<AccountAnemia> {

    private final Configuration configuration;

    private final AccountLoadRequest request;

    public AccountAnemiaLoadAction(Configuration configuration, AccountLoadRequest request) {
        this.configuration = configuration;
        this.request = request;
    }

    @Override
    public LoaderResult<AccountAnemia> perform() {
        AccountAnemia accountAnemias = loadAccountAnemia();
        return new LoaderResult<>(accountAnemias);
    }

    private AccountAnemia loadAccountAnemia() {
		List<Condition> requestFieldsToConditions = mapRequestToConditions();
		return DSL.using(configuration)
                .select()
                .from(Accounts.ACCOUNTS)
                .where(requestFieldsToConditions)
                .fetchOne(this::mapToAccountAnemia);
    }

	private List<Condition> mapRequestToConditions() {
		Optional<AccountId> accountIdOpt = request.getAccountId();
		Optional<String> nameOpt = request.getName();

		List<Condition> conditions = Lists.newArrayList();
		if(accountIdOpt.isPresent()) {
			AccountId accountId = accountIdOpt.get();
			Condition conditionId = Accounts.ACCOUNTS.ID.eq(accountId.getId());
			conditions.add(conditionId);
		}
		if(nameOpt.isPresent()) {
			String name = nameOpt.get();
			Condition conditionName = Accounts.ACCOUNTS.NAME.eq(name);
			conditions.add(conditionName);
		}
		return conditions;
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

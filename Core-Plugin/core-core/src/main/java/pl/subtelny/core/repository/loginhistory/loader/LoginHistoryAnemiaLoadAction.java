package pl.subtelny.core.repository.loginhistory.loader;

import org.jooq.*;
import org.jooq.impl.DSL;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.core.repository.loginhistory.LoginHistoryAnemia;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryId;
import pl.subtelny.repository.LoadAction;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoginHistoryAnemiaLoadAction implements LoadAction<LoginHistoryAnemia> {

    private final Configuration configuration;

    private final LoginHistoryAnemiaLoaderRequest request;

    public LoginHistoryAnemiaLoadAction(Configuration configuration, LoginHistoryLoadRequest request) {
        this.configuration = configuration;
        this.request = LoginHistoryAnemiaLoaderRequest.toRequest(request);
    }

    @Override
    public LoginHistoryAnemia perform() {
        return constructQuery()
                .fetchOne(this::mapToAnemia);
    }

    @Override
    public List<LoginHistoryAnemia> performList() {
        return constructQuery()
                .fetch(this::mapToAnemia);
    }

    private Select<Record> constructQuery() {
        SelectConditionStep<Record> where = DSL.using(configuration)
                .select()
                .from(LoginHistories.LOGIN_HISTORIES)
                .where(request.getWhere());

        List<SortField<Timestamp>> sort = request.getSort();
        SelectLimitStep<Record> limitStep = where;
        if (sort.size() > 0) {
            limitStep = where.orderBy(sort);
        }
        Optional<Integer> limitOpt = request.getLimit();
        if (limitOpt.isPresent()) {
            return limitStep.limit(limitOpt.get());
        }
        return limitStep;
    }

    private LoginHistoryAnemia mapToAnemia(Record record) {
        Integer id = record.get(LoginHistories.LOGIN_HISTORIES.ID);
        UUID uuid = record.get(LoginHistories.LOGIN_HISTORIES.ACCOUNT);
        Timestamp loginTime = record.get(LoginHistories.LOGIN_HISTORIES.LOGIN_TIME);
        Timestamp logoutTime = record.get(LoginHistories.LOGIN_HISTORIES.LOGOUT_TIME);

        LoginHistoryId loginHistoryId = LoginHistoryId.of(id);
        AccountId accountId = AccountId.of(uuid);
        LocalDateTime loginTimeDate = loginTime.toLocalDateTime();
        LocalDateTime logoutTimeDate = logoutTime.toLocalDateTime();
        return new LoginHistoryAnemia(loginHistoryId, accountId, loginTimeDate, logoutTimeDate);
    }
}

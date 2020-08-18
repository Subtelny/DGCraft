package pl.subtelny.core.loginhistory.repository.loader;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import org.jooq.SortField;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.utilities.query.OrderBy;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.utilities.Period;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class LoginHistoryAnemiaLoaderRequest {

    private final List<Condition> where;

    private List<SortField<Timestamp>> sort;

    private Integer limit;

    public LoginHistoryAnemiaLoaderRequest(List<Condition> where, List<SortField<Timestamp>> sort, Integer limit) {
        this.where = where;
        this.sort = sort;
        this.limit = limit;
    }

    public List<Condition> getWhere() {
        return where;
    }

    public List<SortField<Timestamp>> getSort() {
        return sort;
    }

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(limit);
    }

    public static LoginHistoryAnemiaLoaderRequest toRequest(LoginHistoryLoadRequest request) {
        LoginHistoryAnemiaLoaderRequest.Builder builder = new LoginHistoryAnemiaLoaderRequest.Builder();

        Optional<AccountId> accountIdOpt = request.getAccountId();
        if (accountIdOpt.isPresent()) {
            AccountId accountId = accountIdOpt.get();
            builder.where(accountId);
        }

        Optional<Period> periodOpt = request.getPeriod();
        if(periodOpt.isPresent()) {
            Period period = periodOpt.get();
            builder.where(period);
        }

        Optional<Integer> limitOpt = request.getLimit();
        if(limitOpt.isPresent()) {
            Integer limit = limitOpt.get();
            builder.limit(limit);
        }
        return builder.build();
    }

    public static LoginHistoryAnemiaLoaderRequest.Builder newBuilder() {
        return new LoginHistoryAnemiaLoaderRequest.Builder();
    }

    public static class Builder {

        private List<Condition> where = Lists.newArrayList();

        private List<SortField<Timestamp>> sort = Lists.newArrayList();

        private Integer limit;

        public LoginHistoryAnemiaLoaderRequest.Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public LoginHistoryAnemiaLoaderRequest.Builder sortLogoutTime(OrderBy orderBy) {
            switch (orderBy) {
                case DESC:
                    sort.add(LoginHistories.LOGIN_HISTORIES.LOGOUT_TIME.desc());
                    break;
                case ASC:
                    sort.add(LoginHistories.LOGIN_HISTORIES.LOGOUT_TIME.asc());
                    break;
            }
            return this;
        }

        public LoginHistoryAnemiaLoaderRequest.Builder sortLoginTime(OrderBy orderBy) {
            switch (orderBy) {
                case DESC:
                    sort.add(LoginHistories.LOGIN_HISTORIES.LOGIN_TIME.desc());
                    break;
                case ASC:
                    sort.add(LoginHistories.LOGIN_HISTORIES.LOGIN_TIME.asc());
                    break;
            }
            return this;
        }

        public LoginHistoryAnemiaLoaderRequest.Builder where(AccountId accountId) {
            Condition conditionId = LoginHistories.LOGIN_HISTORIES.ACCOUNT.eq(accountId.getInternal());
            where.add(conditionId);
            return this;
        }

        public LoginHistoryAnemiaLoaderRequest.Builder where(Period period) {
            Timestamp loginTime = Timestamp.valueOf(period.getStart());
            Timestamp logoutTime = Timestamp.valueOf(period.getEnd());
            Condition conditionLogin = LoginHistories.LOGIN_HISTORIES.LOGIN_TIME.between(loginTime).and(logoutTime);
            Condition conditionLogout = LoginHistories.LOGIN_HISTORIES.LOGOUT_TIME.between(loginTime).and(logoutTime);
            where.add(conditionLogin);
            where.add(conditionLogout);
            return this;
        }

        public LoginHistoryAnemiaLoaderRequest build() {
            return new LoginHistoryAnemiaLoaderRequest(where, sort, limit);
        }

    }

}

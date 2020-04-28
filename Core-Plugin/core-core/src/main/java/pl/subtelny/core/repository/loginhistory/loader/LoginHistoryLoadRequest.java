package pl.subtelny.core.repository.loginhistory.loader;

import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.repository.loginhistory.OrderBy;
import pl.subtelny.utilities.Period;

import java.util.Optional;

public class LoginHistoryLoadRequest {

    private AccountId accountId;

    private Period period;

    private Integer limit;

    private OrderBy orderBy;

    public LoginHistoryLoadRequest(AccountId accountId, Period period, Integer limit, OrderBy orderBy) {
        this.accountId = accountId;
        this.period = period;
        this.limit = limit;
        this.orderBy = orderBy;
    }

    public Optional<AccountId> getAccountId() {
        return Optional.ofNullable(accountId);
    }

    public Optional<Period> getPeriod() {
        return Optional.ofNullable(period);
    }

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(limit);
    }

    public Optional<OrderBy> getOrderBy() {
        return Optional.ofNullable(orderBy);
    }

    public static LoginHistoryLoadRequest.Builder newBuilder() {
        return new LoginHistoryLoadRequest.Builder();
    }

    public static class Builder {

        private AccountId accountId;

        private Period period;

        private Integer limit;

        private OrderBy orderBy;

        public LoginHistoryLoadRequest.Builder where(AccountId accountId) {
            this.accountId = accountId;
            return this;
        }

        public LoginHistoryLoadRequest.Builder where(Period period) {
            this.period = period;
            return this;
        }

        public LoginHistoryLoadRequest.Builder where(Integer limit) {
            this.limit = limit;
            return this;
        }

        public LoginHistoryLoadRequest.Builder where(OrderBy orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public LoginHistoryLoadRequest build() {
            return new LoginHistoryLoadRequest(accountId, period, limit, orderBy);
        }

    }

}

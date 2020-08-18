package pl.subtelny.core.loginhistory.repository.storage;

import pl.subtelny.core.api.account.AccountId;

import java.util.Objects;

public class LoginHistoryCacheKey {

    private final AccountId accountId;

    private final String query;

    public LoginHistoryCacheKey(AccountId accountId, String query) {
        this.accountId = accountId;
        this.query = query;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getQuery() {
        return query;
    }

    public static LoginHistoryCacheKey of(AccountId accountId, String query) {
        return new LoginHistoryCacheKey(accountId, query);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginHistoryCacheKey that = (LoginHistoryCacheKey) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, query);
    }
}

package pl.subtelny.core.model;

import pl.subtelny.identity.BasicIdentity;

import java.time.LocalDate;

public class LoginHistory extends BasicIdentity<LoginHistoryId> {

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final AccountId accountId;

    public LoginHistory(LoginHistoryId loginHistoryId, LocalDate startDate, LocalDate endDate, AccountId accountId) {
        super(loginHistoryId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.accountId = accountId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}

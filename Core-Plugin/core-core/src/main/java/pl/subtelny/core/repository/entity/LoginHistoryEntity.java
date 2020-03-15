package pl.subtelny.core.repository.entity;

import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.utilities.BasicIdentity;

import java.time.LocalDate;

public class LoginHistoryEntity extends BasicIdentity<LoginHistoryId> {

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final AccountId accountId;

    public LoginHistoryEntity(LoginHistoryId loginHistoryId, LocalDate startDate, LocalDate endDate, AccountId accountId) {
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

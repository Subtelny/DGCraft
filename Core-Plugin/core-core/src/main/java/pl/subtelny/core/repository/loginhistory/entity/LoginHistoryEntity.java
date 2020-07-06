package pl.subtelny.core.repository.loginhistory.entity;

import com.google.common.base.Preconditions;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.loginhistory.LoginHistory;
import pl.subtelny.utilities.identity.BasicIdentity;
import pl.subtelny.utilities.Period;

import java.time.LocalDateTime;

public class LoginHistoryEntity extends BasicIdentity<LoginHistoryId> implements LoginHistory {

    private final LocalDateTime loginTime;

    private final LocalDateTime logoutTime;

    private final AccountId accountId;

    public LoginHistoryEntity(LoginHistoryId loginHistoryId, Period period, AccountId accountId) {
        super(loginHistoryId);
        Preconditions.checkNotNull(period, "Period cannot be null");
        this.loginTime = Preconditions.checkNotNull(period.getStart(), "LoginTime cannot be null");
        this.logoutTime = Preconditions.checkNotNull(period.getEnd(), "LogoutTime cannot be null");
        this.accountId = Preconditions.checkNotNull(accountId, "AccountId cannot be null");
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}

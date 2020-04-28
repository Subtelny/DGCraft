package pl.subtelny.core.repository.loginhistory;

import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryId;

import java.time.LocalDateTime;

public class LoginHistoryAnemia {

    private LoginHistoryId id;

    private AccountId accountId;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    public LoginHistoryAnemia() {
    }

    public LoginHistoryAnemia(LoginHistoryId id, AccountId accountId, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.id = id;
        this.accountId = accountId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public LoginHistoryId getId() {
        return id;
    }

    public void setId(LoginHistoryId id) {
        this.id = id;
    }
}

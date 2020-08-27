package pl.subtelny.core.loginhistory.repository;

import pl.subtelny.core.api.account.AccountId;

import java.time.LocalDateTime;

public class LoginHistoryAnemia {

    private AccountId accountId;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    public LoginHistoryAnemia() {
    }

    public LoginHistoryAnemia(AccountId accountId, LocalDateTime loginTime, LocalDateTime logoutTime) {
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

}

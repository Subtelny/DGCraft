package pl.subtelny.core.repository;

import pl.subtelny.core.model.AccountId;

public class AccountRequest {

    private final AccountId accountId;

    private boolean loadLoginHistory;

    public AccountRequest(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountRequest(AccountId accountId, boolean loadLoginHistory) {
        this.accountId = accountId;
        this.loadLoginHistory = loadLoginHistory;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public boolean isLoadLoginHistory() {
        return loadLoginHistory;
    }

    public static class Builder {

        private final AccountId accountId;

        private boolean loadLoginHistory;

        public Builder(AccountId accountId) {
            this.accountId = accountId;
        }

        public Builder loadLoginHistory(boolean loadLoginHistory) {
            this.loadLoginHistory = loadLoginHistory;
            return this;
        }

        public AccountRequest build() {
            return new AccountRequest(accountId, loadLoginHistory);
        }

    }

}

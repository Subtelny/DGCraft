package pl.subtelny.core.model;

public class AccountDTO {

    private AccountId accountId;

    private String nickname;

    public AccountDTO(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountDTO() {
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }
}

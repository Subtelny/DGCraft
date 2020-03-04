package pl.subtelny.core.repository.loader;

import pl.subtelny.core.model.AccountId;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class AccountAnemia {

    private final AccountId accountId;

    private String name;

    private String displayName;

    private LocalDateTime lastOnline;

    public AccountAnemia() {
        accountId = new AccountId();
    }

    public AccountAnemia(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountAnemia(AccountId accountId, String name, String displayName, LocalDateTime lastOnline) {
        this.accountId = accountId;
        this.name = name;
        this.displayName = displayName;
        this.lastOnline = lastOnline;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDateTime getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(LocalDateTime lastOnline) {
        this.lastOnline = lastOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AccountAnemia that = (AccountAnemia) o;

        return new EqualsBuilder()
                .append(accountId, that.accountId)
                .append(name, that.name)
                .append(displayName, that.displayName)
                .append(lastOnline, that.lastOnline)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accountId)
                .append(name)
                .append(displayName)
                .append(lastOnline)
                .toHashCode();
    }
}

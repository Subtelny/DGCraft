package pl.subtelny.core.repository.entity;

import org.apache.commons.lang.Validate;
import pl.subtelny.core.api.account.AccountId;

import java.time.LocalDateTime;
import java.util.Objects;

public class AccountEntity {

    private final AccountId accountId;

    private String name;

    private String displayName;

    private LocalDateTime lastOnline;

    public AccountEntity(AccountId accountId, String name, String displayName, LocalDateTime lastOnline) {
        this.accountId = accountId;
        this.name = name;
        this.displayName = displayName;
        this.lastOnline = lastOnline;
    }

    public void setDisplayName(String displayName) {
        Validate.notNull(displayName, "DisplayName cannot be null");
        Validate.isTrue(displayName.length() > 0, "DisplayName length is zero");
        this.displayName = displayName;
    }

    public void setName(String name) {
        Validate.notNull(name, "Name cannot be null");
        Validate.isTrue(name.length() > 0, "Name length is zero");
        this.name = name;
    }

    public void setLastOnline(LocalDateTime lastOnline) {
        Validate.notNull(lastOnline, "LocalDate cannot be null");
        this.lastOnline = lastOnline;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLastOnline() {
        return lastOnline;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(lastOnline, that.lastOnline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, name, displayName, lastOnline);
    }
}

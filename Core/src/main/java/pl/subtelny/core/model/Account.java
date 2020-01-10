package pl.subtelny.core.model;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pl.subtelny.core.repository.loader.AccountAnemia;
import pl.subtelny.model.SynchronizedEntity;

import java.time.LocalDateTime;

public class Account extends SynchronizedEntity {

    private final AccountAnemia accountAnemia;

    public Account(AccountAnemia accountAnemia) {
        Validate.notNull(accountAnemia.getAccountId().getId(), "AccountId cannot be null");
        this.accountAnemia = accountAnemia;
    }

    public AccountId getAccountId() {
        return accountAnemia.getAccountId();
    }

    public String getName() {
        return accountAnemia.getName();
    }

    public LocalDateTime getLastOnline() {
        return accountAnemia.getLastOnline();
    }

    public String getDisplayName() {
        return accountAnemia.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        Validate.notNull(displayName, "DisplayName cannot be null");
        Validate.isTrue(displayName.length() > 0, "DisplayName length is zero");
        this.accountAnemia.setDisplayName(displayName);
    }

    public void setName(String name) {
        Validate.notNull(name, "Name cannot be null");
        Validate.isTrue(name.length() > 0, "Name length is zero");
        this.accountAnemia.setName(name);
    }

    public void setLastOnline(LocalDateTime lastOnline) {
        Validate.notNull(lastOnline, "LocalDate cannot be null");
        this.accountAnemia.setLastOnline(lastOnline);
    }

    public AccountAnemia getAccountAnemia() {
        return accountAnemia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return new EqualsBuilder()
                .append(accountAnemia.getAccountId(), account.accountAnemia.getAccountId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(accountAnemia.hashCode())
                .toHashCode();
    }
}

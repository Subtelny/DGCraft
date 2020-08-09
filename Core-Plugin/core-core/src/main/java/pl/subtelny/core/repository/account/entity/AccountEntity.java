package pl.subtelny.core.repository.account.entity;

import org.apache.commons.lang.Validate;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.city.CityId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class AccountEntity implements Account {

    private final AccountId accountId;

    private String name;

    private String displayName;

    private LocalDateTime lastOnline;

    private CityId cityId;

    public AccountEntity(AccountId accountId,
                         String name,
                         String displayName,
                         LocalDateTime lastOnline,
                         CityId cityId) {
        this.accountId = accountId;
        this.name = name;
        this.displayName = displayName;
        this.lastOnline = lastOnline;
        this.cityId = cityId;
    }

    @Override
    public void setDisplayName(String displayName) {
        Validate.notNull(displayName, "DisplayName cannot be null");
        Validate.isTrue(displayName.length() > 0, "DisplayName length is zero");
        this.displayName = displayName;
    }

    @Override
    public void setName(String name) {
        Validate.notNull(name, "Name cannot be null");
        Validate.isTrue(name.length() > 0, "Name length is zero");
        this.name = name;
    }

    @Override
    public void setLastOnline(LocalDateTime lastOnline) {
        Validate.notNull(lastOnline, "LocalDate cannot be null");
        this.lastOnline = lastOnline;
    }

    @Override
    public void setCityId(CityId cityId) {
        Validate.notNull(cityId, "City cannot be null");
        this.cityId = cityId;
    }

    @Override
    public Optional<CityId> getCityId() {
        return Optional.ofNullable(cityId);
    }

    @Override
    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDateTime getLastOnline() {
        return lastOnline;
    }

    @Override
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
                Objects.equals(lastOnline, that.lastOnline) &&
                Objects.equals(cityId, that.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, name, displayName, lastOnline, cityId);
    }
}

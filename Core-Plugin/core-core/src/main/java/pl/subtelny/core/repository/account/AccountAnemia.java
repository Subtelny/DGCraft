package pl.subtelny.core.repository.account;

import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CityType;

import java.time.LocalDateTime;
import java.util.Objects;

public class AccountAnemia {

    private final AccountId accountId;

    private String name;

    private String displayName;

    private LocalDateTime lastOnline;

    private CityType cityType;

    public AccountAnemia() {
        accountId = new AccountId();
    }

    public AccountAnemia(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountAnemia(AccountId accountId, String name, String displayName, LocalDateTime lastOnline, CityType cityType) {
        this.accountId = accountId;
        this.name = name;
        this.displayName = displayName;
        this.lastOnline = lastOnline;
        this.cityType = cityType;
    }

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
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
        AccountAnemia anemia = (AccountAnemia) o;
        return Objects.equals(accountId, anemia.accountId) &&
                Objects.equals(name, anemia.name) &&
                Objects.equals(displayName, anemia.displayName) &&
                Objects.equals(lastOnline, anemia.lastOnline) &&
                cityType == anemia.cityType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, name, displayName, lastOnline, cityType);
    }
}

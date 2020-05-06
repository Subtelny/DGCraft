package pl.subtelny.core.api.account;

import com.google.common.base.Preconditions;

import java.util.Objects;

public class CreateAccountRequest {

    private final AccountId accountId;

    private final String name;

    private final String displayName;

    private final CityType cityType;

    private CreateAccountRequest(AccountId accountId, String name, String displayName, CityType cityType) {
        this.accountId = Preconditions.checkNotNull(accountId, "AccountId cannot be null");
        this.name = Preconditions.checkNotNull(name, "Name cannot be null");
        this.displayName = Preconditions.checkNotNull(displayName, "Display name cannot be null");
        this.cityType = Preconditions.checkNotNull(cityType, "CityType cannot be null");
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public CityType getCityType() {
        return cityType;
    }

    public static CreateAccountRequest of(AccountId accountId, String name, String displayName, CityType cityType) {
        return new CreateAccountRequest(accountId, name, displayName, cityType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAccountRequest that = (CreateAccountRequest) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(displayName, that.displayName) &&
                cityType == that.cityType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, name, displayName, cityType);
    }
}

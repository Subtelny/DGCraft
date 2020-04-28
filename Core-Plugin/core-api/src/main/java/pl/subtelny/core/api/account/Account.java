package pl.subtelny.core.api.account;

import java.time.LocalDateTime;

public interface Account {

    void setDisplayName(String displayName);

    void setName(String name);

    void setLastOnline(LocalDateTime lastOnline);

    void setCityType(CityType cityType);

    CityType getCityType();

    AccountId getAccountId();

    String getName();

    LocalDateTime getLastOnline();

    String getDisplayName();

}
